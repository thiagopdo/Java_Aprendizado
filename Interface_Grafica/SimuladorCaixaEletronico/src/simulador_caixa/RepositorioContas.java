package simulador_caixa;

import javax.swing.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RepositorioContas {
	private static final String DIR_CONTAS = "contas";
	private static final String EXTENSAO = ".txt";
	private static final NumberFormat NF = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

	static {
		NF.setMinimumFractionDigits(2);
		NF.setMaximumFractionDigits(2);
	}

	private final List<Conta> listaContas = new ArrayList<>();

	public RepositorioContas(JFrame janelaPai) {
		prepararDiretorio(janelaPai);
		carregarContas(janelaPai);
	}

	public List<Conta> getListaContas() {
		return listaContas;
	}

	public void salvar(Conta conta, JFrame janelaPai) {
		File arquivo = obterArquivoConta(conta.getNome());

		try(BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
			bw.write("NOME=" + conta.getNome() + "\n");
			bw.newLine();

			bw.write("SENHA=" + conta.getSenha() + "\n");
			bw.newLine();

			bw.write("SALDO=" + NF.format(conta.getSaldo()) + "\n");
			bw.newLine();

			bw.write("HISTORICO_START=");
			bw.newLine();

			for(String linha : conta.getHistorico()) {
				bw.write(linha);
				bw.newLine();
			}

			bw.write("HISTORICO_END=");
			bw.newLine();

		} catch(IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(janelaPai,
							"Erro ao salvar conta: \"" + conta.getNome() + "\".",
							"Erro de gravação",
							JOptionPane.ERROR_MESSAGE);
		}
	}

	public Conta buscar(String nome, String senha) {
		for(Conta c : listaContas) {
			if(c.getNome().equalsIgnoreCase(nome) && c.getSenha().equals(senha)) {
				return c;
			}
		}
		return null;
	}

	public boolean existe(String nome) {
		for(Conta c : listaContas) {
			if(c.getNome().equalsIgnoreCase(nome)) {
				return true;
			}
		}
		return false;
	}

	public void adicionarConta(Conta conta) {
		listaContas.add(conta);
	}

	private void prepararDiretorio(JFrame janelaPai) {
		File dir = new File(DIR_CONTAS);

		if(!dir.exists() && !dir.mkdir()) {
			JOptionPane.showMessageDialog(janelaPai,
							"Falha ao criar pasta \"" + DIR_CONTAS + "\".\n" + "O programa continuará, sem salvar dados em discos",
							"Aviso",
							JOptionPane.WARNING_MESSAGE);
		}
	}

	private void carregarContas(JFrame janelaPai) {
		File dir = new File(DIR_CONTAS);

		if(!dir.exists()) return;

		File[] arquivos = dir.listFiles((d, n) -> n.endsWith(EXTENSAO));

		if(arquivos == null) return;

		for(File arquivo : arquivos) {
			try(BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
				String linha;
				String nome = null, senha = null;
				double saldo = 0.0;

				List<String> hist = new ArrayList<>();

				boolean lendoHistorico = false;

				while((linha = br.readLine()) != null) {
					if(!lendoHistorico) {
						if(linha.startsWith("NOME=")) nome = linha.substring(5);
						else if(linha.startsWith("SENHA=")) senha = linha.substring(6);
						else if(linha.startsWith("SALDO=")) saldo = Double.parseDouble(linha.substring(6));
						else if(linha.startsWith("HISTORICO_START")) lendoHistorico = true;
					} else {
						if(linha.equals("HISTORICO_END")) {
							lendoHistorico = false;
						} else {
							hist.add(linha);
						}
					}

					if(nome != null && senha != null) {
						listaContas.add(new Conta(nome, senha, saldo, hist));
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(janelaPai,
								"Falha ao ler \"" + arquivo.getName() + "\".\n" + "O programa continuará, ignorando esta conta.",
								"Erro de leitura",
								JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private File obterArquivoConta(String nomeConta) {
		String limpo = nomeConta.replaceAll("[^a-zA-Z0-9]", "_");
		return new File(DIR_CONTAS + File.separator + limpo + EXTENSAO);
	}
}

package simulador_caixa;

import javax.swing.*;
import java.awt.*;

public class SimuladorCaixaEletronico extends JFrame implements
				TelaLogin.OuvinteLogin,
				TelaCriarConta.OuvinteCriarConta,
				TelaPrincipal.OuvintePrincipal {

	private final CardLayout layoutCard = new CardLayout();
	private final RepositorioContas repositorio;
	private final TelaLogin telaLogin;
	private final TelaCriarConta telaCriar;
	private final TelaPrincipal telaPrincipal;
	private Conta contaLogada;

	public SimuladorCaixaEletronico() {
		super("Simulador de Caixa Eletronico - Persistente");
		setSize(600, 450);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(layoutCard);

		repositorio = new RepositorioContas(this);
		telaLogin = new TelaLogin(this);
		telaCriar = new TelaCriarConta(this);
		telaPrincipal = new TelaPrincipal(repositorio, this);

		add(telaLogin, "Login");
		add(telaCriar, "Criar");
		add(telaPrincipal, "Principal");
		mostrarTela("Login");
	}

	private void mostrarTela(String nomeCard) {
		layoutCard.show(getContentPane(), nomeCard);
	}

	@Override
	public void aoTentarLogin(String nome, String senha) {

		if(nome.isEmpty() || senha.isEmpty()) {
			JOptionPane.showMessageDialog(this,
							"Preencha nome e senha",
							"Aviso",
							JOptionPane.WARNING_MESSAGE);
			return;
		}

		Conta c = repositorio.buscar(nome, senha);

		if(c == null) {
			JOptionPane.showMessageDialog(this,
							"Conta ou senha incorretos",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
		} else {
			contaLogada = c;
			telaPrincipal.definirConta(contaLogada);
			mostrarTela("Principal");
		}
	}

	@Override
	public void aoSolicitarCriacaoConta() {
		mostrarTela("Criar");
	}

	@Override
	public void aoConfirmarCriacao(String nome, String senha, double saldoInicial) {
		if (repositorio.existe(nome)) {
			JOptionPane.showMessageDialog(this,
							"Já existe uma conta com este nome!",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		Conta nova = new Conta(nome, senha, saldoInicial);
		repositorio.adicionarConta(nova);
		repositorio.salvar(nova, this);

		JOptionPane.showMessageDialog(this,
						"Conta criada com sucesso!",
						"Informação",
						JOptionPane.INFORMATION_MESSAGE);
		mostrarTela("Login");
	}

	@Override
	public void aoVoltarLogin() {
		mostrarTela("Login");
	}

	@Override
	public void aoSolicitarHistorico(Conta conta) {
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setFont(new Font("Monospaced", Font.PLAIN, 12));

		StringBuilder sb = new StringBuilder();

		if(conta.getHistorico().isEmpty()) {
			sb.append("Nenhum movimento registrado");
		}else {
			for(String linha : conta.getHistorico()) {
				sb.append(linha).append("\n");
			}
		}

			area.setText(sb.toString());

			JScrollPane scroll = new JScrollPane(area);
			JOptionPane.showMessageDialog(this,
							scroll,
							"Historio de " + conta.getNome(),
							JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void aoLogout() {
		contaLogada = null;
		mostrarTela("Login");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SimuladorCaixaEletronico app = new SimuladorCaixaEletronico();
			app.setVisible(true);
		});
	}

}

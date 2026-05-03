package reserva;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservaOnibusSwingMongoDB extends JFrame{
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private JPanel painelMapa;
	private JTextField txtData;
	private int capacidade = 20;
	private JButton[] botoesAssentos;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ReservaOnibusSwingMongoDB() {
		inicializarMongo();

		inicializarInterface();

		carregarMapaAssentos();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ReservaOnibusSwingMongoDB janela = new ReservaOnibusSwingMongoDB();
			janela.setVisible(true);
		});
	}

	private void inicializarMongo() {
		mongoClient = MongoClients.create("mongodb://localhost:27017");
		database = mongoClient.getDatabase("reserva_onibus_db");
		collection = database.getCollection("reservas");
	}

	private void inicializarInterface() {
		setTitle("Sistema de Reserva de Passagens");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel painelTopo = new JPanel();
		painelTopo.setLayout(new FlowLayout());
		painelTopo.add(new JLabel("Data (dd/MM/yyyy): "));

		txtData = new JTextField(sdf.format(new Date()), 10);

		painelTopo.add(txtData);

		JButton btnAtualizarMapa = new JButton("Atualizar Mapa");
		btnAtualizarMapa.addActionListener(e -> carregarMapaAssentos());
		painelTopo.add(btnAtualizarMapa);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(e -> abrirJanelaPesquisa());
		painelTopo.add(btnPesquisar);

		painelMapa = new JPanel(new GridLayout(10, 2, 10, 10));

		JScrollPane scrollPane = new JScrollPane(painelMapa);
		add(painelTopo, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

	}

	private void carregarMapaAssentos() {
		painelMapa.removeAll();
		painelMapa.revalidate();
		painelMapa.repaint();
		botoesAssentos = new JButton[capacidade];

		String data = txtData.getText().trim();

		for(int i = 0; i < capacidade; i++) {
			int lugar = i + 1;

			Document filtro = new Document("lugar", lugar).append("dia", data).append("status", "Ativa");

			Document reserva = collection.find(filtro).first();

			boolean reservado = (reserva != null);

			JButton botao = new JButton("Lugar " + lugar);
			botao.setBackground(reservado ? Color.YELLOW : Color.GREEN);
			botao.setOpaque(true);
			botao.setBorderPainted(false);

			botao.addActionListener(e -> {

				if(reservado) {
					abrirJanelaDetalhesReserva(reserva);
				}else{
					abrirJanelaReserva(lugar, data);
				}
			});


			botoesAssentos[i] = botao;
			painelMapa.add(botao);
		}

	}

	private void abrirJanelaDetalhesReserva(Document reserva) {
		ObjectId id = reserva.getObjectId("_id");
		int lugar = reserva.getInteger("lugar", 0);
		String nome = reserva.getString("nome");
		String cpf = reserva.getString("cpf");
		String dia = reserva.getString("dia");
		String status = reserva.getString("status");

		JDialog janelaDetalhes = new JDialog(this, "Detalhes da Reserva", true);
		janelaDetalhes.setSize(450, 350);
		janelaDetalhes.setLocationRelativeTo(this);
		janelaDetalhes.setLayout(new GridLayout(8, 1, 10, 10));

		JTextField txtNome = new JTextField(nome);
		JTextField txtCPF = new JTextField(cpf);
		JTextField txtDia = new JTextField(dia);
		JTextField txtStatus = new JTextField(status);

		janelaDetalhes.add(new JLabel("Nome: "));
		janelaDetalhes.add(txtNome);
		janelaDetalhes.add(new JLabel("CPF: "));
		janelaDetalhes.add(txtCPF);
		janelaDetalhes.add(new JLabel("Dia: "));
		janelaDetalhes.add(txtDia);
		janelaDetalhes.add(new JLabel("Status: "));
		janelaDetalhes.add(txtStatus);

		JPanel painelBotoes = new JPanel(new FlowLayout());

		JButton btnFinalizar = new JButton("Finalizar Reserva");
		btnFinalizar.addActionListener(e -> {
			int resp = JOptionPane.showConfirmDialog(janelaDetalhes,
							"Deseja finalizar a reserva?\n" + "Isso irá liberar o assento para novas reservas.",
							"Confirmar Finalização",
							JOptionPane.YES_NO_OPTION);

			if(resp == JOptionPane.YES_NO_OPTION) {
				String novoNome = txtNome.getText().trim();
				String novoCpf = txtCPF.getText().trim();
				String novoDia = txtDia.getText().trim();

				Document novosDados = new Document("nome", novoNome)
								.append("cpf", novoCpf)
								.append("dia", novoDia)
								.append("status", "Finalizada");

				collection.updateOne(new Document("_id", id), new Document("$set", novosDados));

				JOptionPane.showMessageDialog(janelaDetalhes,
								"Reserva finalizada com sucesso!\n" + "Reserva não aparecerá no mapa, mas estará na pesquisa",
								"Sucesso",
								JOptionPane.INFORMATION_MESSAGE);

				janelaDetalhes.dispose();

				carregarMapaAssentos();
			}
		});
		JButton btnDeletar = new JButton("Deletar Reserva");
		btnDeletar.addActionListener(e -> {
			int resp = JOptionPane.showConfirmDialog(janelaDetalhes,
							"Deseja deletar esta reserva?\n" + "Ela não aparecerá mais no mapa e nem na pesquisa",
							"Confirmar Deleção",
							JOptionPane.YES_NO_OPTION);

			if(resp == JOptionPane.YES_NO_OPTION) {
				collection.deleteOne(new Document("_id", id));
				JOptionPane.showMessageDialog(janelaDetalhes,
								"Reserva deletada com sucesso!",
								"Sucesso",
								JOptionPane.INFORMATION_MESSAGE);

				janelaDetalhes.dispose();
				carregarMapaAssentos();
			}
		});

		painelBotoes.add(btnFinalizar);
		painelBotoes.add(btnDeletar);

		janelaDetalhes.add(painelBotoes, BorderLayout.SOUTH);
		janelaDetalhes.setVisible(true);

	}

	private void abrirJanelaReserva(int lugar, String data) {
		JDialog janelaReserva = new JDialog(this, "Reserva de lugar" + lugar, true);

		janelaReserva.setSize(300, 250);
		janelaReserva.setLocationRelativeTo(this);
		janelaReserva.setLayout(new GridLayout(5, 1, 10, 10));

		JTextField txtNome = new JTextField();
		JTextField txtCPF = new JTextField();

		janelaReserva.add(new JLabel("Nome: "));
		janelaReserva.add(txtNome);

		janelaReserva.add(new JLabel("CPF: "));
		janelaReserva.add(txtCPF);

		JButton btnReservar = new JButton("Reservar");
		btnReservar.addActionListener(e -> {
			String nome = txtNome.getText().trim();
			String cpf = txtCPF.getText().trim();

			if(nome.isEmpty() || cpf.isEmpty()) {
				JOptionPane.showMessageDialog(janelaReserva,
								"Preencha todos os campos");
				return;
			}

			Document reserva = new Document("lugar", lugar)
							.append("nome", nome)
							.append("cpf", cpf)
							.append("dia", data)
							.append("status", "Ativa");

			collection.insertOne(reserva);

			//dispose fecha a janela atual, sem enerrar app principal
			janelaReserva.dispose();

			carregarMapaAssentos();
		});

		janelaReserva.add(btnReservar);
		janelaReserva.setVisible(true);

	}

	private void abrirJanelaPesquisa() {
		JDialog janelaPesquisa = new JDialog(this, "Pesquisa de Reservas", true);
		janelaPesquisa.setSize(1200, 600);
		janelaPesquisa.setLocationRelativeTo(this);
		janelaPesquisa.setLayout(new BorderLayout());

		JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelFiltros.add(new JLabel("Data inicial(dd/MM/yyyy: "));

		JTextField txtDataInicial = new JTextField(10);
		painelFiltros.add(txtDataInicial);

		painelFiltros.add(new JLabel("Data final(dd/MM/yyyy): "));
		JTextField txtDataFinal = new JTextField(10);
		painelFiltros.add(txtDataFinal);

		painelFiltros.add(new JLabel("Nome:"));
		JTextField txtNome = new JTextField(10);
		painelFiltros.add(txtNome);

		painelFiltros.add(new JLabel("CPF:"));
		JTextField txtCPF = new JTextField(10);
		painelFiltros.add(txtCPF);

		painelFiltros.add(new JLabel("Lugar:"));
		JTextField txtLugar = new JTextField(5);
		painelFiltros.add(txtLugar);

		JButton btnPesquisar = new JButton("Pesquisar");
		painelFiltros.add(btnPesquisar);

		DefaultTableModel modeloTabela = new DefaultTableModel();
		JTable tabelaResultados = new JTable(modeloTabela);
		JScrollPane scrollTabela = new JScrollPane(tabelaResultados);

		modeloTabela.addColumn("Lugar");
		modeloTabela.addColumn("Nome");
		modeloTabela.addColumn("CPF");
		modeloTabela.addColumn("Data");
		modeloTabela.addColumn("Status");

		JLabel lblContagem = new JLabel("Total de registros: 0");

		JButton btnExportar = new JButton("Exportar para CSV");

		JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelInferior.add(lblContagem);
		painelInferior.add(btnExportar);

		btnPesquisar.addActionListener(e -> pesquisarEAtualizarTabela(
						txtDataInicial.getText(),
						txtDataFinal.getText(),
						txtNome.getText(),
						txtCPF.getText(),
						txtLugar.getText(),

						modeloTabela,
						lblContagem,
						janelaPesquisa
		));

		btnExportar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				exportarDadosExcel(modeloTabela);
			}
		});


		janelaPesquisa.add(painelFiltros, BorderLayout.NORTH);
		janelaPesquisa.add(scrollTabela, BorderLayout.CENTER);
		janelaPesquisa.add(painelInferior, BorderLayout.SOUTH);

		pesquisarEAtualizarTabela(
						"",
						"",
						"",
						"",
						"",
						modeloTabela,
						lblContagem,
						janelaPesquisa
		);


		janelaPesquisa.setVisible(true);
	}

	private void pesquisarEAtualizarTabela(
					String dataInicialStr,
					String dataFimStr,
					String nomeStr,
					String cpfStr,
					String lugarStr,
					DefaultTableModel modeloTabala,
					JLabel lblContagem,
					JDialog janelaPesquisa
	) {
		modeloTabala.setRowCount(0);

		Document filtro = new Document();

		SimpleDateFormat sdfLocal = new SimpleDateFormat("dd/MM/yyyy");

		Date dataIni = null;
		Date dataFim = null;

		try {

			if(!dataInicialStr.isEmpty()) {
				dataIni = sdfLocal.parse(dataInicialStr);
			}
			if(!dataFimStr.isEmpty()) {
				dataFim = sdfLocal.parse(dataFimStr);
			}
		}catch(ParseException e) {
			JOptionPane.showMessageDialog(janelaPesquisa,
							"Formato de data inválido. Use dd/MM/yyyy",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		if(!nomeStr.isEmpty()) {
			filtro.append("nome", new Document("$regex", ".*" + nomeStr + ".*").append("$options", "i"));
		}

		if(!cpfStr.isEmpty()) {
			filtro.append("cpf", new Document("$regex", ".*" + cpfStr + ".*").append("$options", "i"));
		}

		if(!lugarStr.isEmpty()) {
			try {
				int lugar = Integer.parseInt(lugarStr);
				filtro.append("lugar", lugar);

			}catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(janelaPesquisa,
								"Lugar deve ser um número",
								"Erro",
								JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		List<Document> resultados = new ArrayList<>();

		for(Document doc : collection.find(filtro)) {
			if(dataIni != null && dataFim != null) {
				String diaStr = doc.getString("dia");

				try {
					Date dataRegistro = sdfLocal.parse(diaStr);

					if(dataRegistro.before(dataIni) || dataRegistro.after(dataFim)) {
						continue;
					}

				}catch(ParseException e) {
					continue;
				}
			}

			resultados.add(doc);
		}

		for(Document doc : resultados) {
			Object[] rowData = new Object[5];
			rowData[0] = doc.getInteger("lugar");
			rowData[1] = doc.getString("nome");
			rowData[2] = doc.getString("cpf");
			rowData[3] = doc.getString("dia");
			rowData[4] = doc.getString("status");

			modeloTabala.addRow(rowData);
		}

		lblContagem.setText("Total de registros: " + resultados.size());

	}

	private void exportarDadosExcel(DefaultTableModel modeloTabela) {
		JFileChooser jfc = new JFileChooser();

		jfc.setDialogTitle("Salvar como");

		int userSelection = jfc.showSaveDialog(this);

		File fileToSave = jfc.getSelectedFile();

		if(userSelection == JFileChooser.APPROVE_OPTION) {

			if(!fileToSave.getName().toLowerCase().endsWith(".csv")) {
				fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
			}
		}

		try(PrintWriter pw = new PrintWriter(fileToSave)) {

			for(int col = 0; col < modeloTabela.getColumnCount(); col++) {
				pw.print(modeloTabela.getColumnName(col));
				if(col < modeloTabela.getColumnCount() - 1) {
					pw.print(";");
				}
			}
			pw.println();


			for(int row = 0; row < modeloTabela.getRowCount(); row++) {
				for(int col = 0; col < modeloTabela.getColumnCount(); col++) {
					pw.print(modeloTabela.getValueAt(row, col));
					if(col < modeloTabela.getColumnCount() - 1) {
						pw.print(";");
					}
				}
				pw.println();
			}

			JOptionPane.showMessageDialog(this,
							"Dados exportados com sucesso!",
							"Sucesso",
							JOptionPane.INFORMATION_MESSAGE);

		}catch(Exception e) {
			JOptionPane.showMessageDialog(this,
							"Erro ao exportar dados: " + e.getMessage(),
							"Erro",
							JOptionPane.ERROR_MESSAGE);

		}
	}
}

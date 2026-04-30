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

public class ReservaOnibusSwingMongoDB extends JFrame {
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private JPanel painelMapa;
	private JTextField txtData;
	private int capacidade = 20;
	private JButton[] botoesAssentos;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ReservaOnibusSwingMongoDB() {
		incializarMongo();

		inicializarInterface();

		carregarMapaAssentos();
	}

	private void incializarMongo() {
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
		btnAtualizarMapa.addActionListener(e -> abrirJanelaPesquisa());
		painelTopo.add(btnPesquisar);

		painelMapa = new JPanel(new GridLayout(10,2,10,10));

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

			Document filtro = new Document("lugar", lugar)
							.append("dia", data)
							.append("status", "Ativa");

			Document reserva = collection.find(filtro).first();

			boolean reservado = (reserva != null);

			JButton botao = new JButton("Lugar " + lugar);
			botao.setBackground(reservado ? Color.YELLOW : Color.GREEN);
			botao.setOpaque(true);
			botao.setBorderPainted(false);

			botao.addActionListener(e-> {

				if(reservado) {
					abrirJanelaDetalhesReserva(reserva);
				} else {
					abrirJanelaReserva(lugar, data);
				}
			});


			botoesAssentos[i] = botao;
			painelMapa.add(botao);
		}

	}

	private void abrirJanelaDetalhesReserva(Document reserva) {
	}

	private void abrirJanelaReserva(int lugar, String data) {
	}

	private void abrirJanelaPesquisa() {}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ReservaOnibusSwingMongoDB janela = new ReservaOnibusSwingMongoDB();
			janela.setVisible(true);
		});
	}
}

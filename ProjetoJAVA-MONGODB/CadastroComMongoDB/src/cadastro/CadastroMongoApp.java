package cadastro;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class CadastroMongoApp extends JFrame {
	private JTextField txtNome;
	private JTextField txtEmail;
	private JTextField txtDataNascimento;
	private JTextField txtTelefone;
	private JTextField txtPesquisa;

	private JButton btnNovo;
	private JButton btnSalvar;
	private JButton btnPesquisar;
	private JButton btnAtualizar;
	private JButton btnExcluir;

	private JTable tabela;
	private DefaultTableModel modeloTabela;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public CadastroMongoApp() {
		super("Cadastro com MongoDB - Java Swing");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		conectarMongo();

		criarInterface();

		carregarRegistros("");

		setVisible(true);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> new CadastroMongoApp().setVisible(true));
	}

	private void criarInterface() {
		JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
		JPanel painelForm = new JPanel(new GridLayout(4, 2, 10, 10));
		painelForm.add(new JLabel("Nome:"));
		txtNome = new JTextField(20);
		painelForm.add(txtNome);

		painelForm.add(new JLabel("Email:"));
		txtEmail = new JTextField(20);
		painelForm.add(txtEmail);

		painelForm.add(new JLabel("Telefone"));
		txtTelefone = new JTextField(20);
		painelForm.add(txtTelefone);

		painelForm.add(new JLabel("Data de Nascimento:"));
		txtDataNascimento = new JTextField(10);
		painelForm.add(txtDataNascimento);

		JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		btnSalvar = new JButton("Salvar");
		btnNovo = new JButton("Limpar");
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setEnabled(false);
		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);

		JButton btnExportar = new JButton("Exportar planilha");

		painelBotoesForm.add(btnSalvar);
		painelBotoesForm.add(btnNovo);
		painelBotoesForm.add(btnAtualizar);
		painelBotoesForm.add(btnExcluir);
		painelBotoesForm.add(btnExportar);

		JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));
		painelSuperior.add(painelForm, BorderLayout.CENTER);
		painelSuperior.add(painelBotoesForm, BorderLayout.SOUTH);

		JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelPesquisa.add(new JLabel("Pesquisar:"));
		txtPesquisa = new JTextField(20);

		painelPesquisa.add(txtPesquisa);
		btnPesquisar = new JButton("Pesquisar");
		painelPesquisa.add(btnPesquisar);

		modeloTabela = new DefaultTableModel(new Object[]{"_id", "Nome", "Email", "Telefone", "Data " +
						"de Nascimento"}, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		tabela = new JTable(modeloTabela);
		tabela.setSelectionMode(
						ListSelectionModel.SINGLE_SELECTION
		);
		tabela.getColumnModel().getColumn(0).setMinWidth(0);

		tabela.getColumnModel().getColumn(0).setMaxWidth(0);

		JScrollPane scrollTabela = new JScrollPane(tabela);
		JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
		painelCentral.add(scrollTabela, BorderLayout.CENTER);

		painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
		painelPrincipal.add(painelCentral, BorderLayout.CENTER);
		painelPrincipal.add(painelPesquisa, BorderLayout.SOUTH);
		setContentPane(painelPrincipal);

		btnSalvar.addActionListener(e -> salvarRegistro());

		btnNovo.addActionListener(e -> {
			limparCampos();
			tabela.clearSelection();
		});

		btnPesquisar.addActionListener(e -> {
			String termo = txtPesquisa.getText().trim();
			limparCampos();

			carregarRegistros(termo);
		});

		btnAtualizar.addActionListener(e -> atualizarRegistro());

		btnExcluir.addActionListener(e -> excluirRegistro());

		btnExportar.addActionListener(e -> exportarDadosExcel());

		tabela.getSelectionModel().addListSelectionListener(e -> {
			if(!e.getValueIsAdjusting()) {
				int linha = tabela.getSelectedRow();
				if(linha >= 0) {
					txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
					txtEmail.setText((String) modeloTabela.getValueAt(linha, 2));
					txtTelefone.setText((String) modeloTabela.getValueAt(linha, 3));
					txtDataNascimento.setText((String) modeloTabela.getValueAt(linha, 4));

					btnAtualizar.setEnabled(true);
					btnExcluir.setEnabled(true);

				} else {
					btnAtualizar.setEnabled(false);
					btnExcluir.setEnabled(false);
				}
			}
		});
	}

	private void exportarDadosExcel() {

		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Exportar para Excel(CSV)");

		int userSelection = fileChooser.showSaveDialog(this);

		if(userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();

			if(!fileToSave.getName().toLowerCase().endsWith(".csv")) {
				fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
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
								"Dados exportados com sucesso para " + fileToSave.getAbsolutePath(),
								"Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
				
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this,
								"Erro ao exportar para CSV" + e.getMessage(),
								"Erro",
								JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	private void excluirRegistro() {

		int linha = tabela.getSelectedRow();
		if(linha < 0) {
			JOptionPane.showMessageDialog(this,
							"Selecione um registro para excluir!",
							"Aviso",
							JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idStr = (String) modeloTabela.getValueAt(linha, 0);
		int confirm = JOptionPane.showConfirmDialog(this,
						"Tem certeza que deseja excluir este registro?",
						"Confirmação",
						JOptionPane.YES_NO_OPTION);

		if(confirm != JOptionPane.YES_OPTION) {
			return;
		}

		Object objId = new ObjectId(idStr);
		Document filtro = new Document("_id", objId);
		collection.deleteOne(filtro);

		JOptionPane.showMessageDialog(this,
						"Registo excluído com sucesso!",
						"Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
		carregarRegistros("");

	}

	private void atualizarRegistro() {

		int linha = tabela.getSelectedRow();

		if(linha < 0) {
			JOptionPane.showMessageDialog(this,
							"Selecione um registro para atualizar!",
							"Aviso",
							JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idString = (String) modeloTabela.getValueAt(linha, 0);
		String nome = txtNome.getText().trim();
		String email = txtEmail.getText().trim();
		String telefone = txtTelefone.getText().trim();
		String dataNascimento = txtDataNascimento.getText().trim();

		if(nome.isEmpty() || email.isEmpty()) {
			JOptionPane.showMessageDialog(this,
							"Preencha todos os campos!",
							"Erro",
							JOptionPane.WARNING_MESSAGE);
			return;
		}

		Document novosValores = new Document()
						.append("nome", nome)
						.append("email", email)
						.append("telefone", telefone)
						.append("dataNascimento", dataNascimento);

		Object objId = new ObjectId(idString);
		Document filtro = new Document("_id", objId);
		Document atualizacao = new Document("$set", novosValores);
		collection.updateOne(filtro, atualizacao);

		JOptionPane.showMessageDialog(this,
						"Registro atualizado com sucesso!",
						"Sucesso",
						JOptionPane.INFORMATION_MESSAGE);

		carregarRegistros("");
	}

	private void salvarRegistro() {

		String nome = txtNome.getText().trim();
		String email = txtEmail.getText().trim();
		String telefone = txtTelefone.getText().trim();
		String dataNascimento = txtDataNascimento.getText().trim();
		if(nome.isEmpty() || email.isEmpty()) {
			JOptionPane.showMessageDialog(this,
							"Preencha todos os campos!",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		Document doc = new Document("nome", nome)
						.append("email", email)
						.append("telefone", telefone)
						.append("dataNascimento", dataNascimento);

		collection.insertOne(doc);

		JOptionPane.showMessageDialog(this,
						"Registro inserido com sucesso!",
						"Sucesso",
						JOptionPane.INFORMATION_MESSAGE);

		limparCampos();
		carregarRegistros("");
	}

	private void limparCampos() {
		txtNome.setText("");
		txtEmail.setText("");
		txtTelefone.setText("");
		txtDataNascimento.setText("");
	}

	private void carregarRegistros(String filtro) {
		modeloTabela.setRowCount(0);

		List<Document> resultados = new ArrayList<>();

		if(filtro.isEmpty()) {
			collection.find().into(resultados);
		} else {
			Document cond = new Document("$or", List.of(
							new Document("nome", new Document("$regex", filtro).append("$options", "i")),
							new Document("email", new Document("$regex", filtro).append("$options", "i"))
			));

			collection.find(cond).into(resultados);
		}

		for(Document doc : resultados) {
			Object idObj = doc.getObjectId("_id");

			String nome = doc.getString("nome");
			String email = doc.getString("email");
			String telefone = doc.getString("telefone");
			String dataNascimento = doc.getString("dataNascimento");

			modeloTabela.addRow(new Object[]{idObj.toString(), nome, email, telefone, dataNascimento});
		}
	}

	private void conectarMongo() {
		mongoClient = MongoClients.create("mongodb://localhost:27017/");
		database = mongoClient.getDatabase("cadastroDB");

		collection = database.getCollection("pessoas");
	}
}

package sorteador_numeros;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

//classes awt necessárias para controle de layouts e posicionamento
//Gridbadlayout, insets, cursos components

public final class SorteadorDeNumerosJanela extends JFrame {
	private final JPanel contentPane = new JPanel(new GridBagLayout());
	private final JPanel painelResultado = new JPanel();
	private final JPanel painelRoot = new JPanel();
	private final JLabel rotuloMinimo = new JLabel("Valor mínimo: ");
	private final JLabel rotuloMaximo = new JLabel("Valor máximo: ");
	private final JTextField campoMinimo = new JTextField("1");
	private final JTextField campoMaximo = new JTextField("100");

	private final JButton botaoSortear = new JButton("Sortear");
	private final JLabel rotuloContagem = new JLabel("", SwingConstants.CENTER);
	private final JLabel rotuloResultado = new JLabel("", SwingConstants.CENTER);

	private final SorteioController controller = new SorteioController(this);

	/**
	 * Constructs a new instance of the SorteadorDeNumerosJanela class, setting up the
	 * main graphical user interface for a number randomizer application.
	 *
	 * This constructor performs the following initialization steps:
	 * 1. Sets the window title to "Sorteador de números".
	 * 2. Configures the base window properties such as size, position, and close operation
	 *    by invoking the {@code configurarJanelaBase} method.
	 * 3. Sets up the font styles for various interface components through the {@code configurarFontes} method.
	 * 4. Builds the input panel where the minimum and maximum ranges for randomization
	 *    can be specified, using the {@code montarPainelEntrada} method.
	 * 5. Builds the results panel for displaying the outcomes of the randomization,
	 *    using the {@code montarPainelResultado} method.
	 * 6. Constructs the overall structure of the user interface by combining the input
	 *    and results panels, via the {@code montarEstruturaGeral} method.
	 * 7. Hooks up relevant event listeners for user interactions, using the {@code conectarEventos} method.
	 */

	public SorteadorDeNumerosJanela() {
		super("Sorteador de números");
		configurarJanelaBase();
		configurarFontes();

		montarPainelEntrada();
		montarPainelResultado();
		montarEstruturaGeral();

		conectarEventos();
	}

	private void configurarJanelaBase() {
		setSize(520, 320);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	private void configurarFontes() {
		Font fonteLabel = new Font("SansSerif", Font.PLAIN, 14);
		Font fonteCampo = new Font("SansSerif", Font.PLAIN, 14);
		Font fontBotao = new Font("SansSerif", Font.BOLD, 18);
		Font fontContagem = new Font("SansSerif", Font.BOLD, 16);
		Font fontResultado = new Font("SansSerif", Font.BOLD, 28);

		rotuloMinimo.setFont(fonteLabel);
		rotuloMaximo.setFont(fonteLabel);

		campoMinimo.setFont(fonteCampo);
		campoMaximo.setFont(fonteCampo);

		campoMinimo.setColumns(6);
		campoMaximo.setColumns(6);

		botaoSortear.setFont(fontBotao);
		botaoSortear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		rotuloContagem.setFont(fontContagem);
		rotuloResultado.setFont(fontResultado);
		rotuloResultado.setBackground(new Color(0x006400));
	}

	private void montarPainelEntrada() {
		contentPane.setBorder(new TitledBorder("Configurações do sorteio"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.gridx = 0;
		gbc.gridy = 0;

		contentPane.add(rotuloMinimo);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;

		contentPane.add(campoMinimo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;

		gbc.anchor = GridBagConstraints.LINE_END;
		contentPane.add(rotuloMaximo, gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;

		contentPane.add(campoMaximo, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;

		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;

		contentPane.add(botaoSortear, gbc);

		campoMinimo.setMaximumSize(campoMinimo.getPreferredSize());
		campoMaximo.setMaximumSize(campoMaximo.getPreferredSize());

		botaoSortear.setMaximumSize(botaoSortear.getPreferredSize());
	}

	private void montarPainelResultado() {
		painelResultado.setLayout(new BoxLayout(painelResultado, BoxLayout.Y_AXIS));
		painelResultado.setBorder(new EmptyBorder(12,0,0,0));

		rotuloContagem.setAlignmentX(Component.CENTER_ALIGNMENT);
		rotuloResultado.setAlignmentX(Component.CENTER_ALIGNMENT);

		painelResultado.add(rotuloContagem);
		painelResultado.add(Box.createVerticalStrut(6));
		painelResultado.add(rotuloResultado);

	}

	private void montarEstruturaGeral() {
		painelRoot.setLayout(new BoxLayout(painelRoot, BoxLayout.Y_AXIS));
		painelRoot.setBorder(new EmptyBorder(12,12,12,12));
		painelRoot.add(contentPane);
		painelRoot.add(Box.createVerticalStrut(10));
		painelRoot.add(painelResultado);
		add(painelRoot, BorderLayout.CENTER);
	}

	private void conectarEventos() {
		botaoSortear.addActionListener(controller);
	}

	/**
	 * Metodos acessores - getters
	 * fornecem acesso controlado aos campos de texto da interface, permitindo que a controller obtenha os valores inseridos pelo usuário para o mínimo e máximo do sorteio. Esses métodos são essenciais para a funcionalidade do sorteio, pois permitem que a controller valide e utilize os valores fornecidos na interface para realizar o sorteio de números aleatórios dentro do intervalo especificado.
	 * @return
	 */

	public String getTextoMinimo() {
		return campoMinimo.getText();
	}

	public String getTextoMaximo() {
		return campoMaximo.getText();
	}

	public void exibirContagem(String contagem) {
		rotuloContagem.setText(contagem);
	}

	public void exibirResultado(String resultado) {
		rotuloResultado.setText(resultado);
	}

	public void habilitarBotao(boolean habilitado) {
		botaoSortear.setEnabled(habilitado);
	}
}

package relogio;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

public class RelogioMundialApp extends JFrame{
	private final Map<String, ZoneId> fusosHorarios;
	private final Map<String, JLabel> rotulosHora;
	private final Map<String, JLabel> rotulosData;

	public RelogioMundialApp() {
		super("Relogio Mundial");

		setSize(900,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		fusosHorarios = new LinkedHashMap<>();
		fusosHorarios.put("UTC", ZoneId.of("Etc/UTC"));
		fusosHorarios.put("Nova Iorque", ZoneId.of("America/New_York"));
		fusosHorarios.put("São Paulo", ZoneId.of("America/Sao_Paulo"));
		fusosHorarios.put("Londres", ZoneId.of("Europe/London"));
		fusosHorarios.put("Paris", ZoneId.of("Europe/Paris"));
		fusosHorarios.put("Tôquio", ZoneId.of("Asia/Tokyo"));
		fusosHorarios.put("Sidney", ZoneId.of("Australia/Sydney"));

		rotulosHora = new HashMap<>();
		rotulosData = new HashMap<>();

		configurarLayout();

		iniciarAtualizacao();
	}

	private void configurarLayout() {
		Font fonteTitulo = new Font("Arial", Font.BOLD, 24);
		JLabel titulo = new JLabel("Relógio Mundial", JLabel.CENTER);
		titulo.setFont(fonteTitulo);
		titulo.setForeground(Color.WHITE);
		titulo.setBackground(new Color(0x1E2A38));
		titulo.setOpaque(true);
		add(titulo, BorderLayout.NORTH);

		JPanel painelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelCentral.setBackground(new Color(0x1E2A38));
		painelCentral.setLayout(new javax.swing.BoxLayout(painelCentral, javax.swing.BoxLayout.Y_AXIS));
		painelCentral.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		add(painelCentral, BorderLayout.CENTER);

		Font fonteNome = new Font("Arial", Font.BOLD, 14);
		Font fonteHora = new Font("Arial", Font.BOLD, 14);
		Font fonteData = new Font("Arial", Font.PLAIN, 14);

		for(String cidade : fusosHorarios.keySet()) {
			JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 20,5));
			linha.setBackground(new Color(0x1E2A38));

			JLabel rotuloCidade = new JLabel(cidade);
			rotuloCidade.setFont(fonteNome);
			rotuloCidade.setForeground(Color.WHITE);
			linha.add(rotuloCidade);

			JLabel rotuloHoraLocal = new JLabel();
			rotuloHoraLocal.setFont(fonteHora);
			rotuloHoraLocal.setForeground(new Color(0x00C1D2));
			linha.add(rotuloHoraLocal);

			rotulosHora.put(cidade, rotuloHoraLocal);

			JLabel rotuloDataLocal = new JLabel();
			rotuloDataLocal.setFont(fonteData);
			rotuloDataLocal.setForeground(new Color(0xB0BEC5));
			linha.add(rotuloDataLocal);

			rotulosData.put(cidade, rotuloDataLocal);

			painelCentral.add(linha);

		}

	}

	private void iniciarAtualizacao() {
		Timer temporizador = new Timer(1000, e->atualizarRelogios());
		temporizador.start();
	}

	private void atualizarRelogios() {
		Locale localePT = new Locale("pt", "BR");
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'de' yyyy", localePT);

		DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss", localePT);

		for(Map.Entry<String, ZoneId> fusoHorario : fusosHorarios.entrySet()) {
			String cidade = fusoHorario.getKey();
			ZoneId fuso = fusoHorario.getValue();

			ZonedDateTime agora = ZonedDateTime.now(fuso);

			String horaText = agora.format(formatoHora);

			String dataText = agora.format(formatoData);

			dataText = capitalizarInicio(dataText);

			rotulosHora.get(cidade).setText(horaText);
			rotulosData.get(cidade).setText(dataText);

		}
	}

	private String capitalizarInicio(String texto) {
		if(texto == null || texto.isEmpty()) return texto;

		return texto.substring(0, 1).toUpperCase() + texto.substring(1);
	}
}

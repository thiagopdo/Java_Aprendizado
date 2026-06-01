package Instagram;

import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Sorts.descending;

public class TelaFeed extends JPanel {
	private final InstagramApp app;
	private final JPanel listaPosts = new JPanel();

	public TelaFeed(InstagramApp app) {
		this.app = app;

		setLayout(new BorderLayout());

		setBackground(Color.WHITE);

		add(criarBarraTop(), BorderLayout.NORTH);

		add(criarScrollFeed(), BorderLayout.CENTER);
	}

	private JPanel criarBarraTop() {
		JPanel top = new JPanel(new BorderLayout());

		top.setBorder(new EmptyBorder(8, 12, 8, 12));
		top.setBackground(Color.WHITE);

		JLabel logo = new JLabel("Instagram");

		logo.setFont(new Font("SansSerif", Font.BOLD, 24));

		top.add(logo, BorderLayout.WEST);

		JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));

		nav.setOpaque(false);
		nav.add(botao("Home", app::recarregarFeed));
		nav.add(botao("Pesquisar", () -> app.mostrarTela("pesquisa")));
		nav.add(botao("Perfil", () -> {
			app.setPerfilVisualizado(app.getUsuarioAtual());
			app.mostrarTela("perfil");
		}));

		nav.add(botao("Novo Post", () -> app.mostrarTela("novoPost")));

		nav.add(botao("Sair", app::logout));

		top.add(nav, BorderLayout.EAST);
		return top;
	}

	private JScrollPane criarScrollFeed() {
		listaPosts.setLayout(new BoxLayout(listaPosts, BoxLayout.Y_AXIS));
		listaPosts.setBackground(Color.WHITE);
		listaPosts.setBorder(new EmptyBorder(12, 12, 12, 12));

		JScrollPane scroll = new JScrollPane(listaPosts, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		return scroll;
	}

	private JButton botao(String texto, Runnable acao) {
		JButton b = new JButton(texto);

		b.setFocusPainted(false);
		b.addActionListener(e -> acao.run());

		return b;
	}

	public void recarregar() {
		listaPosts.removeAll();

		var seguindo = new ArrayList<ObjectId>();

		ConexaoMongo.FOLLOWS.find(eq("seguidor_id", app.getUsuarioAtual()
										.getObjectId("_id")))
						.forEach(f -> seguindo.add(f.getObjectId("seguindo_id")));

		seguindo.add(app.getUsuarioAtual().getObjectId("_id"));

		ConexaoMongo.POSTS.find(in("usuario_id", seguindo))
						.sort(descending("criado_em"))
						.forEach(p -> {
							listaPosts.add(new PainelPost(app, p));
							listaPosts.add(Box.createVerticalStrut(16));
						});

		listaPosts.revalidate();
		listaPosts.repaint();
	}

}
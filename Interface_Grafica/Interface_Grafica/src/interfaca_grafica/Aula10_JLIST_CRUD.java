package interfaca_grafica;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Aula10_JLIST_CRUD {
	static class Item {
		final int id;
		String nome;
		Item(int id, String nome) {
			this.id = id;
			this.nome = nome;
		}

		@Override
		public String toString() {
			return nome;
		}
	}

	private final List<Item> dados = new ArrayList<>();
	private int proximoId = 1;

	private JFrame janela;
	private JList<Item> lista;
	private DefaultListModel<Item> modeloFiltrado;
	private JTextField campoNome;
	private JTextField campoPesquisa;
	private JLabel rotuloContagem;
}

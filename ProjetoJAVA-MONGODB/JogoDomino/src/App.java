import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import org.bson.types.ObjectId;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try(PartidaDAO dao = new PartidaDAO()) {

				Partida partida;

				if(args.length == 1) {
					String idHex = args[0];

					if(!idHex.matches("ˆ[0-9a-fA-F]{24}$")) {
						JOptionPane.showMessageDialog(null,
										"ID da partida inválida!\nNecessário 24 caracteres hexadecimais");
						return;
					}


					partida = dao.buscar(new ObjectId(idHex));

					if(partida == null) {
						JOptionPane.showMessageDialog(null,
										"Partida não encontrada!");
						return;
					}

				}else{
					String nome = JOptionPane.showInputDialog("Digite o nome do jogador(Deixe em branco para jogador):");

					if(nome == null)
						return;

					if(nome.isBlank()) {
						nome = "Jogador";
					}

					partida = new Partida(nome, "I.A");

					dao.salvarOuAtualizar(partida);

					JOptionPane.showMessageDialog(null,
									"Partida criada com sucesso!\nID: " + partida.id);
				}

				new TelaPrincipal(partida).setVisible(true);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,
								"Erro ao iniciar a aplicação:\n" + e.getMessage());
			}
		});
	}
}

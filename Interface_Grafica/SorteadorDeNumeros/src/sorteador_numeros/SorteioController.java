package sorteador_numeros;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SorteioController implements ActionListener{

	private final SorteadorDeNumerosJanela ui;
	private Timer temporizador;
	private int contador;


	//ENCAPSULAMENTO
	//permite que a controller consiga acessar e modificar os campos da interface diretamente
	public SorteioController(SorteadorDeNumerosJanela ui) {
		this.ui = ui;
	}


	//sobrescreve o metodo actionPerfomed da interface ActionListener
	//será chamado sempre que o botao Sortear foi acionado
	@Override
	public void actionPerformed(ActionEvent e) {
		iniciarProcedimentoDeSorteio();
	}


	/**
	 * Inicia o procedimento de sorteio de um número aleatório dentro de um intervalo
	 * definido pelo usuário. O processo inclui as seguintes etapas:
	 *
	 * 1. Verificação e validação dos valores mínimo e máximo fornecidos na interface
	 *    do usuário. Se as entradas não forem números inteiros ou se o valor mínimo for
	 *    maior que o valor máximo, mensagens apropriadas de erro ou aviso são exibidas.
	 *
	 * 2. Um temporizador é configurado para iniciar uma contagem regressiva de 5 segundos.
	 *    Durante a contagem regressiva, mensagens de atualização são exibidas na interface
	 *    para informar ao usuário o tempo restante antes do sorteio.
	 *
	 * 3. Quando a contagem atinge 0, o número aleatório é sorteado usando o método
	 *    {@code UtilNumeroAleatorio.sortear(int minimo, int maximo)}. O resultado do sorteio
	 *    é exibido na interface do usuário, juntamente com uma mensagem final indicando
	 *    o fim da contagem.
	 *
	 * 4. Ao final do procedimento, o botão relacionado ao sorteio é reativado.
	 *
	 * O método é projetado para oferecer uma experiência de interação com um sorteador
	 * de números, com validação de entrada e feedback visual durante todo o processo.
	 */
	private void iniciarProcedimentoDeSorteio() {
		ui.exibirContagem("");
		ui.exibirResultado("");

		final int minimo;
		final int maximo;

		try {
		    minimo = Integer.parseInt(ui.getTextoMinimo().trim());
		    maximo = Integer.parseInt(ui.getTextoMaximo().trim());
		} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(
					null,
					"Digite valores numericos inteiros",
					"Erro de Entrada",
					JOptionPane.ERROR_MESSAGE
		);
		return;
		}
		if(minimo>maximo) {
			JOptionPane.showMessageDialog(
							null,
							"O valor minimo não pode ser maior que o máximo",
							"Intervalo inválido",
							JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		ui.habilitarBotao(false);

		contador = 5;

		ui.exibirContagem("Começando em: " + contador);

		temporizador = new Timer(1_000, evento-> {
			contador--;

			if(contador > 0) {
				ui.exibirContagem("Revelando em: " + contador);
			}else {
				temporizador.stop();

				int resultado = UtilNumeroAleatorio.sortear(minimo, maximo);
				ui.exibirContagem("Fim da contagem!");
				ui.exibirContagem("Numero sorteado: " + resultado);
				ui.habilitarBotao(true);
			}
		});

		temporizador.start();
	}
}

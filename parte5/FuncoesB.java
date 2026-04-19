package parte5;

public class FuncoesB {


	public static void main(String[] args) {

		String r1 = verificarAcesso(19, true, true);
		System.out.println(r1);
		String r2 = verificarAcesso(17, true, false);
		System.out.println(r2);
		String r3 = verificarAcesso(20, true, false);
		System.out.println(r3);


		System.out.println(obterDiaDaSemana(9));


		verificarAutenticacao("admin", "SenhaSegura");
		System.out.println("Oi");

		System.out.println(calcularMedia(10, 20, 30));



	}


	/**
	 * Verifica se o acesso deve ser permitido ou negado com base na idade, posse de carteira e histórico negativo.
	 *
	 * @param idade                a idade da pessoa que solicita acesso
	 * @param temCarteira          indica se a pessoa possui carteira (true para sim, false para não)
	 * @param temHistoricoNegativo indica se a pessoa possui histórico negativo (true para sim, false para não)
	 * @return uma mensagem indicando se o acesso foi permitido ou negado, com a justificativa
	 */
	public static String verificarAcesso(int idade, boolean temCarteira, boolean temHistoricoNegativo) {
		if (idade >= 18 && temCarteira && !temHistoricoNegativo) {
			return "Acesso permitido: todos critérios atendidos";
		} else if (idade >= 18 && temCarteira && temHistoricoNegativo) {
			return "Acesso negado: Histórico negativo";
		} else {
			return "Acesso negado: Critérios não atendidos";
		}
	}

	/**
	 * Retorna o nome do dia da semana correspondente ao número fornecido.
	 *
	 * @param dia o número do dia da semana, onde 1 representa "Segunda-feira" e 7 representa "Domingo"
	 * @return o nome do dia da semana correspondente ao número fornecido, ou "Dia invalido" se o número não estiver entre
	 * 1 e 7
	 */
	public static String obterDiaDaSemana(int dia) {
		return switch (dia) {
			case 1 -> "Segunda-feira";
			case 2 -> "Terça-feira";
			case 3 -> "Quarta-feira";
			case 4 -> "Quinta-feira";
			case 5 -> "Sexta-feira";
			case 6 -> "Sabado";
			case 7 -> "Domingo";
			default -> "Dia invalido";
		};
	}


	/**
	 * Verifica as credenciais de autenticação de um usuário.
	 */
	public static void verificarAutenticacao(String usuario, String senha) {
		if (!usuario.equals("admin") && !senha.equals("SenhaSegura")) {
			System.out.println("Acesso negado");
			System.exit(1);
		}

		System.out.println(("Autenticao bem sucedida"));
	}


	/**
	 * Calcula média de 3 notas
	 *
	 * @param num1 primeiro numero a ser enviado
	 * @param num2 primeiro numero a ser enviado
	 * @param num3 primeiro numero a ser enviado
	 * @return média dos tres numeros
	 */
	public static double calcularMedia(int num1, int num2, int num3) {
		return (double) (num1 + num2 + num3) / 3;
	}
}

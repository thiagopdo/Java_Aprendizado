package parte12;

import java.io.*;

public class Erros {
	public static void main(String[] args) {
		//trycatch

//		try {
//			int x = 10 / 0;
//
//			System.out.println(x);
//		} catch(ArithmeticException e) {
//			System.out.println("Divisao por zero nao é possivel");
//		}
//
//		try {
//			int[] numeros = {1, 2, 4};
//
//			System.out.println(numeros[3]);
//		} catch(Exception error) {
//			System.out.println("Erro ao acessar o array");
//			System.out.println("mesg: " + error.getMessage());
//		}

		//trycatch finnally
//		try {
//
//			int[] numeros = {1, 2, 4};
//			System.out.println(numeros[2]);
//		} catch(ArrayIndexOutOfBoundsException e) {
//			System.out.println("Erro ao acessar o array");
//			System.out.println("mesg: " + e.getMessage());
//		} finally {
//			System.out.println("Executou finally");
//		}

		//verificadas e nao verificadas
		try {
			BufferedReader reader = new BufferedReader(new FileReader("arquivo.txt"));
			String linha = reader.readLine();
			System.out.println(linha);
		} catch(Exception e) {
			System.out.println("erro ao tentar abrir arquivo " + e.getMessage());


		}

		//nao verificada
//		String text = null;
//		System.out.println(text.length());

		//EXCECAO COM THROW

		try {
			validarIdade(20);
			validarIdade(10);
		} catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}

		//EXCECOES CUSTOMIZADAS
		Banco minhaConta = new Banco(5000);
		try {
			minhaConta.sacar(6000);
		} catch(SaldoInsuficienteException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		//THROWS em metodos
		try {
			processarArquivo("/var/www/arquivo.txt");
		} catch(FileNotFoundException e) {
			System.out.println("erro: " + e.getMessage());
		} catch(IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		//ENCADEAMENTO DE EXCECOES
		try {
			abrirArquivo(null);
		} catch(Exception e) {
			System.out.println("Mensagem: " + e.getMessage());
			System.out.println("Causa original: " + e.getCause());
		}

		//multiplos catches
		try {
			processarArquivo("asda");
		} catch(NullPointerException | IOException e) {
			System.out.println("erro multictach" + e.getMessage());
		}

		//RELANÇAR EXCECOES
		try {
			processarDados(null);
		} catch(Exception e) {
			System.out.println("Outra coisa...");

			System.out.println("MSg: " + e.getMessage());

			System.out.println("Pilha de execuçõ" + e.getStackTrace());
		}
	}

	public static void validarIdade(int idade) {
		if(idade < 18) {
			throw new IllegalArgumentException("Idade invalida, deve ser maior que 18 anos");
		}
		System.out.println("Idade validada com sucesso: " + idade);
	}

	public static void processarArquivo(String caminho) throws FileNotFoundException, IOException {
		if(caminho == null || caminho.isEmpty()) {
			throw new IOException("Caminhho inválido");
		}

		File arquivo = new File(caminho);

		if(!arquivo.exists()) {
			throw new FileNotFoundException("Arquivo não encontrado");
		}

		System.out.println("Arquivo encontrado com sucesso!");
	}

	public static void abrirArquivo(String caminho) {
		try {
			if(caminho == null) {
				throw new NullPointerException("Caminho nulo!");
			}

			throw new FileNotFoundException("Arquivo não encontrado");
		} catch(FileNotFoundException e) {

			NullPointerException npe = new NullPointerException("Erro ao processar arquivo");

			npe.initCause(e);

			throw npe;
		}
	}

	public static void processarDados(String dados) throws Exception {
		try {
			if(dados == null) {
				throw new NullPointerException("Os dados sao nulos");
			}
		} catch(Exception e) {
			System.out.println("Tratamento, criação de logs, ...");
			throw e;
		}
	}

}
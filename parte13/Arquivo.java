package parte13;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Arquivo {
	public static void main(String[] args) {
//		//LEITURA DE ARQUIVO
		String currentDir = System.getProperty("user.dir") + "/parte13/";
		System.out.println(currentDir);
		System.out.println("/Users/thunder/Desktop/Projetos/JAVA/parte13");
//
//
//		//FileReader = caracter por caracter
//		//BufferedReader = linha por linha
//		try (FileReader reader = new FileReader(currentDir + "arquivo.txt")) {
//
//			int caracter;
//
//			while((caracter = reader.read()) != -1) {
//				System.out.print((char) caracter);
//			}
//
//		} catch (Exception e) {
//			System.out.println("Erro ao ler arquivo: " + e.getMessage());
//		}
//
//		System.out.println(" -Fim deste arquivo");
//
//		//BUFFERED REDER
//		try (BufferedReader reader = new BufferedReader(new FileReader(currentDir + "arquivo.txt"))) {
//
//			String linha;
//
//			while((linha = reader.readLine()) != null) {
//				System.out.println(linha);
//			}
//
//		} catch (Exception e) {
//			System.out.println("Erro ao ler arquivo: " + e.getMessage());
//		}
//
//		//ESCREVER  em ARQUIVOS
//		//FileWriter
//		try (FileWriter writer = new FileWriter(currentDir + "saida.txt")) {
//
//			writer.write("Escrevendo em arquivo\n");
//			writer.write("Escrevendo em arquivo 2");
//
//		} catch (Exception e) {
//			System.out.println("Erro ao escrever arquivo: " + e.getMessage());
//		}
//
//		//BufferedWriter
//		try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentDir + "saida2.txt"))) {
//			writer.write("Escrevendo em arquivo com o bufferedwriter");
//			writer.newLine();
//			writer.write("Escrevendo em arquivo 2 com o bufferedwriter");
//			writer.newLine();
//
//		} catch (Exception e) {
//			System.out.println("Erro ao escrever arquivo: " + e.getMessage());
//		}
//
//		try (BufferedWriter writer =
//						     new BufferedWriter(new FileWriter(currentDir + "saida2.txt", true))) {
//			writer.append("testando append");
//
//		} catch (Exception e) {
//			System.out.println("Erro ao escrever arquivo: " + e.getMessage());
//		}
//
//
//		//SERIALIZAÇãO DE OBJETOS
//		Pessoa pessoa = new Pessoa("Thiago", 38);
//
//		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentDir + "pessoa.ser"))) {
//
//			oos.writeObject(pessoa);
//
//			System.out.println("Objeto serializado com sucesso!");
//
//		} catch (Exception e) {
//			System.out.println("Erro ao serializar objeto: " + e.getMessage());
//		}
//
//		//DESERIALIZAÇAO
//		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentDir + "pessoa" +
//						".ser"))) {
//
//			Pessoa pessoa2 = (Pessoa) ois.readObject();
//
//			System.out.println("Nome: " + pessoa2.getNome());
//			System.out.println("Idade: " + pessoa2.getIdade());
//
//		} catch (Exception e) {
//			System.out.println("Erro ao deserializar objeto: " + e.getMessage());
//		}
//
//		//MANIPULACAO DE BINARIOS
//		//imagem
//		try (
//						FileInputStream fis = new FileInputStream(currentDir + "imagem.jpg");
//						FileOutputStream fos = new FileOutputStream(currentDir + "copia_imagem.jpg")) {
//
//			int byteData;
//
//			while((byteData = fis.read()) != -1) {
//				fos.write(byteData);
//			}
//
//			System.out.println("Arquivo copiado com sucesso!");
//
//		} catch (Exception e) {
//			System.out.println("Erro ao copiar arquivo: " + e.getMessage());
//		}
//
//		//video
//		try (
//						BufferedInputStream bis = new BufferedInputStream(new FileInputStream(currentDir +
//										"video.mkv"));
//						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(currentDir +
//										"copia_video.mkv"))
//		) {
//			byte[] buffer = new byte[1024]; //buffer 1kb em 1kb
//			int bytesLidos;
//
//			while((bytesLidos = bis.read(buffer)) != -1) {
//				bos.write(buffer, 0, bytesLidos);
//			}
//
//			System.out.println("VIdeo copiado com sucessso");
//
//		} catch (Exception e) {
//
//			System.out.println("Erro ao copiar video: " + e.getMessage());
//		}
//
//		//MANIPULAÇAO DE IMAGEM
//		//imagem.jpg é colocar texto
//
//		try {
//			BufferedImage imagem = ImageIO.read(new File(currentDir + "imagem.jpg"));
//
//			if(imagem == null) {
//				System.out.println("Imagem não pode ser carregada");
//				return;
//			}
//
//			Graphics2D g2d = imagem.createGraphics();
//
//			//preparando texto
//			g2d.setFont(new Font("Arial", Font.BOLD, 50));
//			FontMetrics fm = g2d.getFontMetrics();
//			String text = "Text no CENTRO";
//
//			//centralizar texto
//			int larguraText = fm.stringWidth(text);
//			int alturaText = fm.getHeight();
//
//			//posicionamento
//			int x = (imagem.getWidth() - larguraText) / 2;
//			int y = (imagem.getHeight() - alturaText) / 2 + fm.getAscent();
//
//			//desenhar retangulo
//			g2d.setColor(Color.BLACK);
//			g2d.fillRect(x - 10, y - fm.getAscent(), larguraText + 20, alturaText);
//
//			//desenhar o texto em cima do retangulo
//			g2d.setColor(Color.RED);
//			g2d.drawString(text, x, y);
//
//			//liberar recursos
//			g2d.dispose();
//
//			//salver imagem
//			File outputFile = new File(currentDir + "imagem_com_txt.png");
//
//			ImageIO.write(imagem, "png", outputFile);
//
//			System.out.println("Gerou o texto na imagem com sucesso!");
//
//
//		} catch (Exception e) {
//			System.out.println("Erro ao processar imagem: " + e.getMessage());
//		}
//
//		//MANIPULACAO DE ARQUIVOS E DIRETORIOS
//		//criacao de diretorios
//		Path caminhoDiretorio = Paths.get(currentDir + "diretorioNovo");
//		try {
//			if(!Files.exists(caminhoDiretorio)) {
//				Files.createDirectories(caminhoDiretorio);
//
//				System.out.println("Diretorio criado com sucesso: " + caminhoDiretorio.toString());
//			} else {
//				System.out.println("Diretório já existe!");
//			}
//		} catch (Exception e) {
//			System.out.println("Erro ao criar diretório: " + e.getMessage());
//		}
//
//
//		//criar copiar e mover arquivos
//		Path caminhoArquivoOriginal = Paths.get(currentDir + "arquivo_criado.txt");
//		Path caminhoArquivoCopy = Paths.get(currentDir + "arquivo_criado_copia.txt");
//		Path caminhoArquivoMoved = Paths.get(currentDir, "diretorioNovo", "arquivo_criado_movido.txt");
//
//		try {
//			//criar
//			if(!Files.exists(caminhoArquivoOriginal)) {
//				Files.createFile(caminhoArquivoOriginal);
//				System.out.println("Arquivo criado");
//			}
//
//			//copiar
//			if(!Files.exists(caminhoArquivoCopy)) {
//				Files.copy(caminhoArquivoOriginal, caminhoArquivoCopy);
//			}
//
//			//mover
//			Files.move(caminhoArquivoCopy, caminhoArquivoMoved);
//		} catch (Exception e) {
//			System.out.println("Erro ao criar diretório: " + e.getMessage());
//		}

		//ARQUIVOS TEMPORARIOS
//		try {
//
//			Path arquivoTemp = Files.createTempFile("meuTempFile", ".txt");
//			System.out.println("Arquivo criado em: " + arquivoTemp.toAbsolutePath());
//			Files.writeString(arquivoTemp, "Conteudo temporarios");
//
//			String conteudo = Files.readString(arquivoTemp);
//
//			System.out.println("Conteudo: " + conteudo);
//
//			Files.deleteIfExists(arquivoTemp);
//
//		} catch (Exception e) {
//			System.out.println("Erro ao criar aquivo tempo: " + e.getMessage());
//
//		}

		//TRABALHANDO COM ARQUIVOS ZIP
		//comprimir
		Path arquivoOriginal = Paths.get(currentDir + "arquivo.txt");
		Path arquivoZip = Paths.get(currentDir + "arquivo_comprimido.zip");

		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(arquivoZip.toFile()));
		     FileInputStream fiz = new FileInputStream(arquivoOriginal.toFile())) {

			//cria uma entrada zip para o arquivo
			ZipEntry zipEntry = new ZipEntry(arquivoOriginal.getFileName().toString());

			zos.putNextEntry(zipEntry);

			//le o conteudo do arquivo e grava no zip
			byte[] buffer = new byte[1024];
			int bytedRead;

			while((bytedRead = fiz.read(buffer)) != -1) {
				zos.write(buffer, 0, bytedRead);
			}

			//fechar a entrada do zip
			zos.closeEntry();
			System.out.println("Arquivo compactado com sucesso.");

		} catch (Exception e) {
			System.out.println("Erro ao criar aquivo tempo: " + e.getMessage());
		}

		//descompactar
		Path arquivoZipado = Paths.get(currentDir + "arquivo_comprimido.zip");
		Path destino = Paths.get(currentDir + "descompactado");

		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(arquivoZipado.toFile()))) {

			ZipEntry zipEntry;
			//criar dir se nao existir
			if(!Files.exists(destino)) {
				Files.createDirectories(destino);
			}

			//iterar em cada um dos arquivos do zip
			while((zipEntry = zis.getNextEntry()) != null) {
				//criar arquivo
				Path caminhoDestino = destino.resolve(zipEntry.getName());

				try (FileOutputStream fos = new FileOutputStream(caminhoDestino.toFile())) {

					//le arquivo do zip e grava no destino
					byte[] buffer = new byte[1024];
					int bytedRead;

					while((bytedRead = zis.read(buffer)) != -1) {
						fos.write(buffer, 0, bytedRead);
					}


				}

				System.out.println("Arquivo descompactado com sucesso. " + caminhoDestino);
				zis.closeEntry();
			}

		} catch (Exception e) {
			System.out.println("Erro ao descompactar arquivo: " + e.getMessage());
		}

		//Manipulacao de arquivos csv
		//leitura de arquivo csv
		String arquivoCSV = currentDir + "dados.csv";
		String linha;
		String separador = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(arquivoCSV))) {

			while((linha = br.readLine()) != null) {
				//separar a string
				String[] dados = linha.split(separador);
				System.out.println("Nome: " + dados[0] + ", Idade: " + dados[1] + ", Cidade: " + dados[2]);
			}


		} catch (Exception e) {
			System.out.println("Erro ao ler arquivo csv: " + e.getMessage());
		}

		//escrita em arquivos csv
		String arquivoEscrivaCSV = currentDir + "dadosEscrita.csv";

		try (FileWriter writer = new FileWriter(arquivoEscrivaCSV)) {
			//insercao linha a linha com separador
			writer.append("Nome,Idade,Cidade\n");
			writer.append("Isabel,23,SP\n");
			writer.append("Joao,22,Goiania\n");
			writer.append("Robson,34,Maceio\n");
			writer.append("Cleiton,45,MAnaus\n");

			System.out.println("Arquivo csv escrito com sucesso!");


		} catch (Exception e) {
			System.out.println("Erro ao escrever arquivo csv: " + e.getMessage());
		}
	}
}
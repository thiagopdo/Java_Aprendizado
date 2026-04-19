package parte8;

public class Exer1 {
	public static void main(String[] args) {
//		Celular iphone = new Celular();
//
//		iphone.marca = "Apple";
//		iphone.modelo = "Iphone 13";
//		iphone.ligar();
//		iphone.usar(10);
//		iphone.desligar();
//
//		Celular xiaomi = new Celular();
//		xiaomi.marca = "Xiaomi";
//		xiaomi.modelo = "Redmi Note 11";
//		xiaomi.ligar();
//		xiaomi.usar(10);
//		xiaomi.desligar();

		//exer2

		Aluno thiago = new Aluno("THiago", 123123123, 8.9);

		System.out.println(thiago.getNome());
		System.out.println(thiago.getNotafinal());

		thiago.setNome("Thiago oliveira");
		thiago.setNotaFinal(7.8);

		thiago.exibirInfo();

		//exer4
		ProdutoEletronico celular = new ProdutoEletronico("Redmi note 11", 1999.9, 12);
		celular.exibirInfo();
		System.out.println(celular.getNome());
		celular.aplicarDesconto(10);
		celular.exibirInfo();

		//exer5
		Biblioteca livro1 = new Biblioteca("Senhos dos aneis", "Tolkien", true);
		livro1.exibirInfo();
		System.out.println(livro1.estaDisponivel());
		livro1.exibirInfo();
		livro1.devolver();
		livro1.exibirInfo();
		livro1.pegarEmprestado();


	}
}

package parte11;

public class POO {
	public static void main(String[] args) {
		// OBJECT COMPOSITION

		Motor motor1 = new Motor("V8", 450);

		Carro carro1 = new Carro("Ford", "Mustang", motor1);

		carro1.exibirInfo();

		// HERANÇA
		Cachorro adilson = new Cachorro("Adilson");
		adilson.latir();
		adilson.emitirSom();

		Animal leao = new Animal("Boina");
		leao.emitirSom();

		// CLASSE OBJECT
//		Pessoa thiago = new Pessoa("Thiago", 37);
//		Pessoa pedro = new Pessoa("Pedro", 20);
//		System.out.println(thiago.toString());
//		System.out.println(thiago.equals(pedro));
//		System.out.println(thiago.hashCode());

		// OVERRIDE
		Quadrado q1 = new Quadrado(2);
		Circulo c1 = new Circulo(4);

		System.out.println(q1.calcularArea());
		System.out.println(c1.calcularArea());

		// SUPER
		Funcionario func1 = new Funcionario("Carlos", 3000);
		Gerente gerente = new Gerente("Marcos", 5000, 1000);

		func1.exibirDetalhes();
		gerente.exibirDetalhes();

		System.out.println("func1 = " + func1.calcularBonus());
		System.out.println("Gerente = " + gerente.calcularBonus());

		// CLASSE ABSTRACT
		InstrumentoMusical violao = new Violao("Violão");
		InstrumentoMusical bateria = new Bateria("Bateria");

		violao.exibirDetalhes();
		bateria.exibirDetalhes();

		violao.tocar();
		bateria.tocar();

		// INTERFACES
		Pagamento cartao = new CartaoCredito();
		Pagamento transferencia = new TransferenciaBancaria();

		cartao.processarPagamento(100);
		cartao.exibirRecibo(100);

		transferencia.processarPagamento(200);
		transferencia.exibirRecibo(200);

		//multiplas INTERFACES
		Documento doc = new Documento("Arquivo text");
		doc.imprimir();
		doc.salvar();
		doc.instrucaoSalvar();

		//default methods na interface
		CalculadoraAvançado calc = new CalculadoraAvançado();
		System.out.println(calc.somar(10, 20));
		System.out.println(calc.multiplicar(16, 67));

		//POLIMORFISMO
		//classes abstratas ou interfaces, -> sobrescrever os metodos destas superclasses
		InstrumentoMusical violino = new Violino("Violino");

		violino.exibirDetalhes();
		violino.tocar();
	}
}
package parte11.Exercicios;

public class Exercicio {
	public static void main(String[] args) {
		Moto moto = new Moto();
		moto.acelerar();
		Carro carro = new Carro();
		carro.acelerar();

		Veiculo veiculo = new Veiculo();
		veiculo.acelerar();

		//exer 2
		Cachorro cachorro = new Cachorro("Doguinho", "Au au au", "Pitbull");
		cachorro.exibirDetalhes();

		Animal cat = new Animal("Cat", "Miau miau");
		cat.emitirSom();

		//exer3

		FuncionarioTempoIntegral fti = new FuncionarioTempoIntegral("Ana", 4000);
		FuncionarioMeioPeriodo fmp = new FuncionarioMeioPeriodo("Luzi", 2000, 6);

		fti.adicionarBeneficio("Plano de saúde");
		fmp.adicionarBeneficio("Vale alimentação");

		//exer4
		Pilotavel meuHidroaviao = new HIdroAviao();
		Navegavel meuBarco = new Barco();
		Pilotavel meuAviao = new Aviao();

		meuAviao.pilotar();
		meuBarco.navegar();
		meuHidroaviao.pilotar();

		operarVeiculo(meuAviao);
		operarVeiculo(meuBarco);
		//operarVeiculo(meuHidroaviao);
	}

	public static void operarVeiculo(Object veiculo) {
		if(veiculo instanceof Pilotavel) {
			System.out.println("Este veiculo é pilotavel");
		}

		if(veiculo instanceof Navegavel) {
			System.out.println("Este veiculo é navegavel");
		}
	}
}

package parte11.Exercicios;

public class FuncionarioMeioPeriodo extends Funcionario implements Beneficios {

	private double salarioPorHora;
	private int horasTrabalhadas;

	public FuncionarioMeioPeriodo(String nome, double salarioPorHora, int horasTrabalhadas) {
		super(nome);
		this.salarioPorHora = salarioPorHora;
		this.horasTrabalhadas = horasTrabalhadas;
	}

	@Override
	public double calcularSalario() {
		return salarioPorHora * horasTrabalhadas;
	}

	@Override
	public void adicionarBeneficio(String beneficio) {
		System.out.println("Adicionando beneficio para meio período: " + beneficio);
	}
}

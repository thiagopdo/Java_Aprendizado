package parte12;

public class Banco {
	private double saldo;

	public Banco(double saldo) {
		this.saldo = saldo;
	}

	public void sacar(double valor) throws SaldoInsuficienteException {
		if(valor > saldo) {
			//exception
			throw new SaldoInsuficienteException("Saldo insuficiente para operação: " + valor);
		}
		saldo -= valor;

		System.out.println("valor do saque: " + valor);
	}
}

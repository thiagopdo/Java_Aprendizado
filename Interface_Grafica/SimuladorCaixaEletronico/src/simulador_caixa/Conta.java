package simulador_caixa;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Conta {
	private static final NumberFormat NF = NumberFormat.getNumberInstance(
					new Locale("pt", "BR")
	);

	static {
		NF.setMinimumFractionDigits(2);
		NF.setMaximumFractionDigits(2);
	}

	private final String nome;
	private final String senha;
	private final List<String> historico;
	private double saldo;

	//construtor da classe Conta, recebe 4 parametros, nome, senha, saldo e um
	// historico existente, caso o historico seja nulo, ele inicia um novo ArrayList
	// para o historico, caso contrario ele adiciona o historico existente ao novo
	// ArrayList criado
	public Conta(String nome, String senha, double saldo, List<String> historicoExistente) {
		this.nome = nome;
		this.senha = senha;
		this.saldo = saldo;
		this.historico = new ArrayList<>();

		if(historicoExistente != null) {
			this.historico.addAll(historicoExistente);
		}
	}

	//construtor da classe Conta, recebe 3 parametros, utilizado qnd usuário está
	//criando uma nova conta
	public Conta(String nome, String senha, double saldoInicial) {

		//chama o construtor da classe principal Conta(overloading), reutilizando o
		// código do construtor principal, passando o nome, senha, saldoInicial e null
		// para o historicoExistente, pois a conta está sendo criada do zero, sem um
		// historico existente
		this(nome, senha, saldoInicial, null);
		this.historico.add("Conta criada com saldo de R$ " + NF.format(saldoInicial));
	}

	//setters e getters para os atributos da classe Conta, permitindo acessar e
	// modificar os valores dos atributos nome, senha, saldo e historico
	public String getNome() {
		return nome;
	}

	public String getSenha() {
		return senha;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double valor) {
		saldo = valor;
	}

	public List<String> getHistorico() {
		return historico;
	}

	public void adicionarHistorico(String linha) {
		historico.add(linha);
	}
}



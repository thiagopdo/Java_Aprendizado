package parte8;

public class ContaBancaria {
  private String titular;
  private double saldo;

  public void setTitular(String titular) {

    // logica para validar
    if (titular != null && !titular.isEmpty()) {
      this.titular = titular;
    } else {
      System.out.println("Titular inválido");
    }
  }

  public void setSaldo(double saldo) {
    if (saldo >= 0) {
      this.saldo = saldo;
    } else {
      System.out.println("Saldo inválido");
    }
  }

  public String getInfoTitular() {
    System.out.println("Titular da conta: " + this.titular + " - Saldo: " + this.saldo);
    return this.titular;
  }

  public String getTitular() {
    return titular;
  }

  public String getSaldo() {
    return "R$ " + saldo;
  }

}

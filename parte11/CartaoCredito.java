package parte11;

public class CartaoCredito implements Pagamento {

  @Override
  public void processarPagamento(double valor) {
    System.out.println("Processando pagamento de R$ " + valor + " com cartão de crédito.");

  }

}

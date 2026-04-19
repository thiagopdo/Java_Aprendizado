package parte11;

public class TransferenciaBancaria implements Pagamento {

  @Override
  public void processarPagamento(double valor) {
    System.out.println("Processando transferência bancária de R$ " + valor + ".");
  }

  @Override
  public void exibirRecibo(double valor) {
    System.out.println("Recibo: Transferência bancária de R$ " + valor + " processada pelo banco.");
  }

}
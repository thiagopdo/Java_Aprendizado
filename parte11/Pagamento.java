package parte11;

interface Pagamento {
  // metodo abstrato
  void processarPagamento(double valor);

  // metodo default
  default void exibirRecibo(double valor) {
    System.out.println("Recibo: Pagamento de R$ " + valor + " processado.");
  }
}

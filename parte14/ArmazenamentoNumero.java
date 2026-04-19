package parte14;

public class ArmazenamentoNumero implements Armazenamento<Integer> {
  public Integer numero;

  @Override
  public void salvar(Integer item) {
    this.numero = item;
  }

  @Override
  public Integer recuperar() {
    return numero;
  }

}

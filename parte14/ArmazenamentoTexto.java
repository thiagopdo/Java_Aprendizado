package parte14;

public class ArmazenamentoTexto implements Armazenamento<String> {

  private String texto;

  @Override
  public void salvar(String item) {
    this.texto = item;
  }

  @Override
  public String recuperar() {
    return this.texto;
  }

}

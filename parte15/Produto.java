package parte15;

public class Produto {
  String nome;
  String categoria;

  public Produto(String nome, String categoria) {
    this.nome = nome;
    this.categoria = categoria;
  }

  @Override
  public String toString() {
    return nome;
  }

}

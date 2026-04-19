package parte8;

public class Pessoa {
  private String nome;
  private int idade;

  public void setNome(String nome) {
    // THIS -> referece ao OBJETO
    this.nome = nome;
  }

  public void setIdade(int idade) {
    //this -> referece ao OBJETO
    this.idade = idade;
  }

  public String getNome() {
    return nome;
  }

  public int getIdade() {
    return idade;
  }
}

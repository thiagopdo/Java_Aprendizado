package parte17;

public class Usuario {
  @NotEmpty(message = "O nome é obrigatório")
  private String nome;

  @NotEmpty(message = "O email é obrigatório")
  private String email;

  public Usuario(String nome, String email) {
    this.nome = nome;
    this.email = email;
  }

}

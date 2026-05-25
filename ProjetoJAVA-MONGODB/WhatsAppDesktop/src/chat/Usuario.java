package chat;

import org.bson.types.Binary;


public class Usuario {
	public Object id;
	public String nome;
	public String email;
	public String senha;
	public Binary fotoPerfilBytes;
	public String status;
	public String telefone;

	public Usuario(Object id, String nome, String email, String senha, Binary fotoPerfilBytes, String status, String telefone) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.fotoPerfilBytes = fotoPerfilBytes;
		this.status = status;
		this.telefone = telefone;
	}
}

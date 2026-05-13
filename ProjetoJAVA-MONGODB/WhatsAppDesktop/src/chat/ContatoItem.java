package chat;

import org.bson.types.Binary;
import org.bson.types.ObjectId;

public class ContatoItem {
	public ObjectId id;
	public String nome;
	public String email;
	public Binary fotoPerfilBytes;


	public ContatoItem(ObjectId id, String nome, String email, Binary fotoPerfilBytes) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.fotoPerfilBytes = fotoPerfilBytes;
	}
}

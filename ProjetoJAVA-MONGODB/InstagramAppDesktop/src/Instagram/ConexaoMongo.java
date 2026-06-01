package Instagram;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ConexaoMongo {
	private static final MongoClient client = MongoClients.create("mongodb://localhost:27017");
	private static final MongoDatabase database = client.getDatabase("instagram");

	public static final MongoCollection<Document> USUARIOS = database.getCollection("usuarios");
	public static final MongoCollection<Document> POSTS = database.getCollection("posts");
	public static final MongoCollection<Document> FOLLOWS = database.getCollection("follows");
	public static final MongoCollection<Document> SESSIONS = database.getCollection("sessions");
	public static final MongoCollection<Document> CHATS = database.getCollection("chats");
	public static final MongoCollection<Document> MENSAGENS = database.getCollection("mensagens");


}

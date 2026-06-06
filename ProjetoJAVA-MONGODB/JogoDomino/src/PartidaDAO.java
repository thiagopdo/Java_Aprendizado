import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class PartidaDAO implements AutoCloseable {

	private MongoClient mongoClient;
	private final MongoCollection<Document> colPartidas;

	public PartidaDAO() {
		mongoClient = MongoClients.create("mongodb://localhost:27017");

		MongoDatabase db = mongoClient.getDatabase("domino_db");

		colPartidas = db.getCollection("partidas");
	}

	public void salvarOuAtualizar(Partida partida) {
		if(partida.id == null) {
			partida.id = new ObjectId();
		}

		colPartidas.replaceOne(
						eq("_id", partida.id),
						partida.toDocument(),
						new com.mongodb.client.model.ReplaceOptions().upsert(true)
		);
	}

	public Partida buscar(ObjectId id) {
		Document doc = colPartidas.find(
						eq("_id", id)
		).first();

		return doc == null ? null : Partida.fromDocument(doc);
	}

	@Override
	public void close() {
		mongoClient.close();
	}
}

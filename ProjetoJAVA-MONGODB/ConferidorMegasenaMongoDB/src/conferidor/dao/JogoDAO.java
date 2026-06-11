package conferidor.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import conferidor.modelo.Jogo;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO {
	private final MongoCollection<Jogo> col;

	public JogoDAO() {
		col = ConexaoMongoDB.get().db()
						.getCollection("jogos", Jogo.class);
	}

	public void inserir(Jogo jogo) {
		col.insertOne(jogo);
	}

	public List<Jogo> listarTodos() {
		List<Jogo> lista = new ArrayList<>();

		col.find()
						.sort(new Document("data", 1))
						.into(lista);

		return lista;
	}

	public List<Jogo> buscarPorData(LocalDate data) {
		List<Jogo> jogos = new ArrayList<>();

		col.find(Filters.eq("data", data))
						.into(jogos);

		return jogos;
	}

	public void atualizarAcertos(ObjectId id, int acertos) {
		col.updateOne(
						Filters.eq("_id", id),
						Updates.set("acertos", acertos)
		);
	}

	public void excluirPorId(ObjectId idCurto) {
		col.deleteOne(Filters.regex("_id", idCurto + "$"));
	}
}

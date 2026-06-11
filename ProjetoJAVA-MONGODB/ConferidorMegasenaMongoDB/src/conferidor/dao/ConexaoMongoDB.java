package conferidor.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Collection;
import java.util.Collections;

public class ConexaoMongoDB {

	private static ConexaoMongoDB instancia;
	private final MongoClient cliente;
	private final MongoDatabase banco;

	private ConexaoMongoDB() {

		CodecRegistry pojoCodec = CodecRegistries.fromProviders(
						PojoCodecProvider.builder()
										.automatic(true)
										.build()
		);

		CodecRegistry codecsAtivos = CodecRegistries.fromRegistries(
						MongoClientSettings.getDefaultCodecRegistry(),
						pojoCodec
		);

		MongoClientSettings settings = MongoClientSettings.builder()
						.applyToClusterSettings(builder ->
										builder.hosts(Collections.singletonList(
														new ServerAddress(
																		"localhost",
																		27017
														)))
						).codecRegistry(codecsAtivos)
						.build();

		this.cliente = MongoClients.create(settings);
		this.banco = cliente.getDatabase("conferidor");
	}

	public static synchronized ConexaoMongoDB get() {
		if(instancia == null) {
			instancia = new ConexaoMongoDB();
		}
		return instancia;
	}

	public MongoDatabase db() {
		return banco;
	}

	public void fechar() {
		cliente.close();
	}
}

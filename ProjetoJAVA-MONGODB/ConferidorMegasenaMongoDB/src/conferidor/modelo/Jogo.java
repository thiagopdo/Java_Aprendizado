package conferidor.modelo;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Jogo {
	private ObjectId id;
	private List<Integer> dezenas;
	private LocalDate data;
	private int acertos = 0;

	public Jogo() {}

	public Jogo(Collection<Integer> dezenas, LocalDate data) {
		this.id = new ObjectId();
		this.dezenas = new ArrayList<>(new TreeSet<>(dezenas));

		if(this.dezenas.size() < 6 || this.dezenas.size() > 20) {
			throw new IllegalArgumentException("Quantidade de dezenas deve ficar entre 6 e 20!");
		}

		this.data = data;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public List<Integer> getDezenas() {
		return dezenas;
	}

	public void setDezenas(List<Integer> d) {
		this.dezenas = d;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getAcertos() {
		return acertos;
	}

	public void setAcertos(int acertos) {
		this.acertos = acertos;
	}

	public String dezenasComoString() {
		return dezenas.toString();
	}

	public String getDataFormatada() {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public String getIdCurto() {
		return id.toHexString().substring(18);
	}

	public int contarAcerto(int[] sorteadas) {
		int count = 0;

		for(int n : sorteadas) {
			if(dezenas.contains(n)) {
				count++;
			}
		}
		return count;
	}
}

package Desafios.Desafios_Treinamento.src.Desafios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Desafio8 {
	private static final String[] mesesPorExtenso = {
					"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
	};

	public static String dataPorExtenso(String dataStr) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			formato.setLenient(false);

			Date data = formato.parse(dataStr);
			Calendar calendario = Calendar.getInstance();

			calendario.setTime(data);

			int dia = calendario.get(Calendar.DAY_OF_MONTH);
			int mes = calendario.get(Calendar.MONTH);
			int ano = calendario.get(Calendar.YEAR);

			return dia + " de " + mesesPorExtenso[mes] + " de " + ano;

		} catch (ParseException e) {
			return "NULL";
		}
	}


	public static void main(String[] args) {
		String dataTeste = "10/10/2026";
		String dataTeste2 = "31/02/2020";
		String dataTeste3 = "31/05/2020";

		System.out.println(dataPorExtenso(dataTeste));
		System.out.println(dataPorExtenso(dataTeste2));
		System.out.println(dataPorExtenso(dataTeste3));

	}
}

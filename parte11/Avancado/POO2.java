package parte11.Avancado;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class POO2 {
	//REFLECTION API


	//CLASSES com FINAL
	// classe ou metodo final nao pode ser sobrescrito
	public static void main(String[] args) {
		ContaBancaria conta = new ContaBancaria(500);

		System.out.println("Conta: " + conta.getSaldo());

		try {
			Class<?> classePessoa = Class.forName("parte11.Avancado.Pessoa");

			Constructor<?> constructor = classePessoa.getConstructor(String.class, int.class);

			Object pessoa = constructor.newInstance("João", 23);

			Method metodoDizerOla = classePessoa.getMethod("dizerOla");

			metodoDizerOla.invoke(pessoa);

			Field campNome = classePessoa.getDeclaredField("nome");

			campNome.setAccessible(true);
			campNome.set(pessoa, "Maria");
			metodoDizerOla.invoke(pessoa);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}

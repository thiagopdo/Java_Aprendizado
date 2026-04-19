package parte17;

import java.lang.reflect.Field;

public class Anotacoes {
  public static void main(String[] args) {
    Cachorro cachorro = new Cachorro();
    cachorro.emitirSom();
    cachorro.testeMover();

    // anotations customizada
    Exemplo exemplo = new Exemplo();

    for (var metodo : exemplo.getClass().getDeclaredMethods()) {
      System.out.println(metodo);

      if (metodo.isAnnotationPresent(Executar.class)) {
        Executar anotacao = metodo.getAnnotation(Executar.class);
        for (int i = 0; i < anotacao.vezes(); i++) {
          // invoca metodo
          try {
            metodo.invoke(exemplo);

          } catch (Exception e) {
            System.out.println("Erro ao invocar o método: " + e.getMessage());
          }
        }
      }
    }

    // anotations para validar acampos
    Usuario usuario = new Usuario("", "joao@gmail.com");
    validarCampos(usuario);

    Usuario usuario2 = new Usuario("Maria", "");
    validarCampos(usuario2);

  }

  // validar campos anotados
  public static void validarCampos(Object obj) throws IllegalArgumentException {

    Class<?> classe = obj.getClass();

    for (Field campo : classe.getDeclaredFields()) {
      if (campo.isAnnotationPresent(NotEmpty.class)) {
        // saber valor de cada dampo
        NotEmpty anotacao = campo.getAnnotation(NotEmpty.class);

        campo.setAccessible(true);

        try {
          Object valor = campo.get(obj);
          if (valor == null || valor.toString().isEmpty()) {
            System.out.println(anotacao.message());
          }
        } catch (Exception e) {
          System.out.println("Erro ao acessar o campo: " + e.getMessage());
        }

      }

      // anotations com processador

      Servico servico = new Servico();
      try {
        LogProcessor.processarLogs(servico);
      } catch (Exception e) {
        System.out.println("Erro ao invocar o método: " + e.getMessage());
      }

    }
  }

}

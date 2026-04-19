package parte17;

import java.io.*;
import java.lang.reflect.Method;

public class LogProcessor {

  public static void processarLogs(Object obj) throws Exception {
    Class<?> classe = obj.getClass();

    String currentDir = System.getProperty("user.dir") + "/parte17/";

    for (Method metodo : classe.getDeclaredMethods()) {

      if (metodo.isAnnotationPresent(Log.class)) {

        metodo.setAccessible(true);

        try (FileWriter writer = new FileWriter(currentDir + "metodos_log.txt", true)) {

          writer.write("Executando o metodo " + metodo.getName() + "\n");
          metodo.invoke(obj);

        } catch (Exception e) {
          System.out.println("Erro ao escrever no arquivo: " + e.getMessage());

        }
      }
    }
  }
}
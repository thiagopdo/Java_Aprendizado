package parte16;

import java.util.regex.*;

public class Regex {
  public static void main(String[] args) {

    // 1. quantificadores
    String regex = "a*"; // zero ou mais 'a'
    String texto = "b aaab aa ba";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(texto);

    System.out.println("Encontrado a*: ");
    while (matcher.find()) {
      // find - encontrar
      System.out.println("'" + matcher.group() + "'");
    }

    regex = "a+"; // uma ou mais 'a'
    texto = "b aaab aa ba";

    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);

    System.out.println("Encontrado a+: ");
    while (matcher.find()) {
      // find - encontrar
      System.out.println("'" + matcher.group() + "'");
    }

    regex = "a{2,4}"; // duas a quatro 'a'
    texto = "b aaab aa ba";

    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);

    System.out.println("Encontrado a{2,4}: ");
    while (matcher.find()) {
      // find - encontrar
      System.out.println("'" + matcher.group() + "'");
    }

    regex = "a{2}"; // duas 'a'
    texto = "b aaab aa ba";

    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);

    System.out.println("Encontrado a{2}: ");
    while (matcher.find()) {
      // find - encontrar
      System.out.println("'" + matcher.group() + "'");
    }

    // Ancoras e fronteiras
    // ^ - início da linha
    regex = "^c";
    texto = "casa carro cadeira";

    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);

    System.out.println("Encontrado ^c: ");
    while (matcher.find()) {
      System.out.println("'" + matcher.group() + "'");
    }

    // $ - fim da linha
    regex = "a$";
    texto = "casa carro cadeira";

    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);

    System.out.println("Encontrado a$: ");
    while (matcher.find()) {
      System.out.println("'" + matcher.group() + "'");
    }

    // fronteira \b - palavra completa
    regex = "\\bpalavra\\b"; // palavra completa
    texto = "palavra palavra-chave palavras";

    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);

    System.out.println("Encontrado \\bpalavra\\b: ");
    while (matcher.find()) {
      System.out.println("'" + matcher.group() + "'");
    }

    // fronteira \B - palavra completa
    regex = "\\Bpalavra"; // palavra completa
    texto = "palavra compalavra apalavras";

    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);

    System.out.println("Encontrado \\Bpalavra\\B: ");
    while (matcher.find()) {
      System.out.println("'" + matcher.group() + "'");
    }

    // grupos e capturas
    regex = "(\\d{2})-(\\d{2})-(\\d{4})"; // palavra completa
    texto = "a data de hoje é 31-03-2026 e a data de amanhã é 01-04-2026";

    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);

    System.out.println("Encontrado (\\d{2})-(\\d{2})-(\\d{4}): ");
    while (matcher.find()) {
      System.out.println("Dia: " + matcher.group(1));
      System.out.println("Mês: " + matcher.group(2));
      System.out.println("Ano: " + matcher.group(3));
    }

    // backreferences - referência a grupos anteriores
    // $1 - referência ao primeiro grupo
    // $2 - referência ao segundo grupo
    // $3 - referência ao terceiro grupo
    String textoSubstituido = texto.replaceAll("(\\d{2})-(\\d{2})-(\\d{4})", "$1/$2/$3");
    System.out.println("Texto substituído: " + "$1/$2/$3");
    System.out.println("Texto substituído: " + textoSubstituido);

    // pattern and matcher - correspondência e substituição
    // correnspondecia parcial
    regex = "\\d{3}"; // três dígitos
    texto = "123ABC456";
    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);

    System.out.println("Encontrado \\d{3}: ");
    if (matcher.lookingAt()) {
      System.out.println("Encontrado no início: " + matcher.group());
    }

    // contando grupo com groupCount
    regex = "(\\d{3})-(\\d{3})-(\\d{3})"; // palavra completa
    texto = "123-456-789";
    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);
    if (matcher.matches()) {
      System.out.println("Número de grupos: " + matcher.groupCount());

      for (int i = 0; i <= matcher.groupCount(); i++) {
        System.out.println("Grupo " + i + ": " + matcher.group(i));
      }
    }

    // start e end para obter a poisição do grupo encontrado
    regex = "\\d{3}"; // três dígitos
    texto = "o codigo é 123 e o segundo codigo é 456";
    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(texto);
    System.out.println("Posiçao das correspondências de \\d{3}: ");
    while (matcher.find()) {
      System.out.println("Encontrado: " + matcher.group() + " na posição " + matcher.start() + " a " + matcher.end());
    }

    // quotes para escapar caracteres especiais
    String literalRegex = Pattern.quote("1+1=2");
    texto = "A equação correta é 1+1=2 e é o resultado do exercicio";
    pattern = Pattern.compile(literalRegex);
    matcher = pattern.matcher(texto);
    if (matcher.find()) {
      System.out.println("Encontrado a expressão literal: " + matcher.group());
    }

    // expressoes avançadas
    // look-ahead - correspondência que é seguida por um padrão específico
    String regexLookAhead = "\\d+(?=\\$)";
    texto = "O preço é 100$ e o desconto é 20%, o preço final é 80$";
    pattern = Pattern.compile(regexLookAhead);
    matcher = pattern.matcher(texto);
    if (matcher.find()) {
      System.out.println("Encontrado com look-ahead: " + matcher.group());
    }

    // lookbehind - correspondência que é precedida por um padrão específico
    String regexLookBehind = "(?<=\\$)\\d+";
    texto = "O preço é 100$ e o desconto é 20%, o preço final é $80 ou $100";
    pattern = Pattern.compile(regexLookBehind);
    matcher = pattern.matcher(texto);
    if (matcher.find()) {
      System.out.println("Encontrado com look-behind: " + matcher.group());
    }

  }
}

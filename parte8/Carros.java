package parte8;

public class Carros {
  // atributos e propriedades
  String marca;
  String modelo;
  int ano;
  double velocidadeAtual = 0;
  boolean motorLigado = false;

  // metodos
  void acelerar() {
    System.out.println("O carro está acelerando.");
  }

  void exibirInfo() {
    System.out.println("Marca: " + marca);
    System.out.println("Modelo: " + modelo);
    System.out.println("Ano: " + ano);
  }

  // METODOS

  void ligarMotor() {
    if (!motorLigado) {
      motorLigado = true;
      System.out.println("O motor foi ligado.");
    } else {
      System.out.println("O motor já está ligado.");
    }
  }

  void aumentarVelocidade(double incremento) {
    if (motorLigado) {
      velocidadeAtual += incremento;
      System.out.println("Velocidade atual: " + velocidadeAtual + " km/h");
    } else {
      System.out.println("O motor está desligado. Ligue o motor para acelerar.");
    }
  }
}

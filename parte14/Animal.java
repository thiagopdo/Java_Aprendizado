package parte14;

public class Animal implements Voador, Nadador {
  public void mover() {
    System.out.println("O animal está se movendo.");
  }

  @Override
  public void voar() {
    System.out.println("O animal está voando.");
  }

  @Override
  public void nadar() {
    System.out.println("O animal está nadando.");
  }

}

package parte17;

public class Cachorro extends Animal {
  @Override
  public void emitirSom() {
    System.out.println("O cachorro late.");
  }

  @SuppressWarnings("deprecation")
  public void testeMover() {
    mover();
  }
}

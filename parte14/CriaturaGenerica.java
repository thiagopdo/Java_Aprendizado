package parte14;

public class CriaturaGenerica<T extends Animal & Nadador & Voador> {
  private T criatura;

  public CriaturaGenerica(T critura) {
    this.criatura = critura;
  }

  public void usarHabilidades() {
    criatura.mover();
    criatura.nadar();
    criatura.voar();
  }

}

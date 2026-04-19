package parte17;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) // @Retention define o tempo de vida da anotação, nesse caso, em tempo de execução
@Target(ElementType.METHOD) // @Target define onde a anotação pode ser aplicada, nesse caso, em métodos
@interface Executar{
  int vezes() default 1;
}
package parte17;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface  NotEmpty {
  String message() default "O campo não pode ser vazio";
  
}

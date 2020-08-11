package tr.com.yigithanbalci.shoppingcartservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class ToppingNotFoundException extends RuntimeException{

  public ToppingNotFoundException(String message){
    super(message);
  }
}

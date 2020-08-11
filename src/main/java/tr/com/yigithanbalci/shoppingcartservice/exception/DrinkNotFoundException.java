package tr.com.yigithanbalci.shoppingcartservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class DrinkNotFoundException extends RuntimeException{

  public DrinkNotFoundException(String message){
    super(message);
  }
}

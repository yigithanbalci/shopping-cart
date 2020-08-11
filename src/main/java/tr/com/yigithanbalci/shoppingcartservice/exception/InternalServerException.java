package tr.com.yigithanbalci.shoppingcartservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException {

  public InternalServerException(String message){
    super(message);
  }
}

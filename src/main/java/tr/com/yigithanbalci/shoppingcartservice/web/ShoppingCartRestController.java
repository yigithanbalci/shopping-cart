package tr.com.yigithanbalci.shoppingcartservice.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ShoppingCartRestController {

  @PutMapping("/{userId}/cart")
  public ResponseEntity addItemToCart(@RequestBody String item, @PathVariable("userId") String userId){

  }

  @DeleteMapping("/{userId}/cart")
  public ResponseEntity deleteItemFromCart(@RequestBody String item, @PathVariable("userId") String userId){

  }

  @PutMapping("/{userId}/cart/checkout")
  public ResponseEntity checkoutShoppingCart(@PathVariable("userId") String userId){

  }

}

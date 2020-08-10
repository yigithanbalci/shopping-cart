package tr.com.yigithanbalci.shoppingcartservice.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsRestController {

  @PostMapping("/drinks")
  public ResponseEntity createDrink(@RequestBody String drink){

  }

  @PostMapping("/toppings")
  public ResponseEntity createTopping(@RequestBody String topping){

  }

  @PutMapping("/drinks/{drinkId}")
  public ResponseEntity updateDrink(@RequestBody String drink, @PathVariable("drinkId") String drinkId){

  }

  @PutMapping("/toppings/{toppingId}")
  public ResponseEntity updateTopping(@RequestBody String topping, @PathVariable("toppingId") String toppingId){

  }

  @DeleteMapping("/drinks/{drinkId}")
  public ResponseEntity deleteDrink(@PathVariable("drinkId") String drinkId){

  }

  @DeleteMapping("/toppings/{toppingId}")
  public ResponseEntity deleteTopping(@PathVariable("toppingId") String toppingId){

  }
}

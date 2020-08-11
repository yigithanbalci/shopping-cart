package tr.com.yigithanbalci.shoppingcartservice.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsCustomerRestController {

  @NonNull private final DrinkService drinkService;
  @NonNull private final ToppingService toppingService;

  @GetMapping("/toppings")
  public ResponseEntity getAllToppings(){
    return ResponseEntity.ok().build();
  }

  @GetMapping("/drinks")
  public ResponseEntity getAllDrinks(){
    return ResponseEntity.ok().build();
  }
}

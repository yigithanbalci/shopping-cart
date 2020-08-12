package tr.com.yigithanbalci.shoppingcartservice.web;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.yigithanbalci.shoppingcartservice.exception.DrinkNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.exception.InternalServerException;
import tr.com.yigithanbalci.shoppingcartservice.exception.ToppingNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
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
  public ResponseEntity<List<ToppingEntity>> getAllToppings(){
    try {
      List<ToppingEntity> toppingEntities = toppingService.findAll();
      return ResponseEntity.ok(toppingEntities);
    } catch (ToppingNotFoundException e){
      log.error("Not found any toppings in database.");
      throw e;
    } catch (Exception e) {
      log.error("Exception occurs while getting toppings {}", e.getLocalizedMessage(), e);
      throw new InternalServerException("Toppings could not be retrieved: " + e.getLocalizedMessage());
    }
  }

  @GetMapping("/drinks")
  public ResponseEntity<List<DrinkEntity>> getAllDrinks(){
    try {
      List<DrinkEntity> drinkEntities = drinkService.findAll();
      return ResponseEntity.ok(drinkEntities);
    } catch (DrinkNotFoundException e){
      log.error("Not found any drinks in database.");
      throw e;
    } catch (Exception e) {
      log.error("Exception occurs while getting drinks {}", e.getLocalizedMessage(), e);
      throw new InternalServerException("Drinks could not be retrieved: " + e.getLocalizedMessage());
    }
  }
}

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
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.exception.DrinkNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.exception.InternalServerException;
import tr.com.yigithanbalci.shoppingcartservice.exception.ToppingNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;

@Slf4j
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductsAdminRestController {

  private final DrinkService drinkService;
  private final ToppingService toppingService;

  // TODO: 12.08.2020 argumanlari final yap.
  @PostMapping("/drinks")
  public ResponseEntity<DrinkEntity> createDrink(@RequestBody final Drink drink) {
    try {
      DrinkEntity drinkEntity = drinkService.createDrink(DrinkEntity.from(drink));
      return ResponseEntity.ok(drinkEntity);
    } catch (Exception e) {
      log.error("Exception occurs while creating drink {} : {}", drink, e.getLocalizedMessage(), e);
      throw new InternalServerException("Drink could not be created: " + e.getLocalizedMessage());
    }
  }

  @PostMapping("/toppings")
  public ResponseEntity<ToppingEntity> createTopping(@RequestBody Topping topping) {
    try {
      ToppingEntity toppingEntity = toppingService.createTopping(ToppingEntity.from(topping));
      return ResponseEntity.ok(toppingEntity);
    } catch (Exception e) {
      log.error("Exception occurs while creating topping {} : {}", topping, e.getLocalizedMessage(),
          e);
      throw new InternalServerException("Topping could not be created: " + e.getLocalizedMessage());
    }
  }

  @PutMapping("/drinks/{drinkId}")
  public ResponseEntity<DrinkEntity> updateDrink(@RequestBody Drink drink,
      @PathVariable("drinkId") Long drinkId) {
    try {
      DrinkEntity drinkEntity = DrinkEntity.from(drink);
      drinkEntity.setId(drinkId);
      DrinkEntity updatedDrinkEntity = drinkService.updateDrink(drinkEntity);
      return ResponseEntity.ok(updatedDrinkEntity);
    } catch (DrinkNotFoundException e) {
      log.error("Drink not found with Id: " + drinkId);
      throw e;
    } catch (Exception e) {
      log.error("Exception occurs while updating drink {} : {}", drink, e.getLocalizedMessage(), e);
      throw new InternalServerException("Drink could not be updated: " + e.getLocalizedMessage());
    }
  }

  @PutMapping("/toppings/{toppingId}")
  public ResponseEntity<ToppingEntity> updateTopping(@RequestBody Topping topping,
      @PathVariable("toppingId") Long toppingId) {
    try {
      ToppingEntity toppingEntity = ToppingEntity.from(topping);
      toppingEntity.setId(toppingId);
      ToppingEntity updatedToppingEntity = toppingService.updateTopping(toppingEntity);
      return ResponseEntity.ok(updatedToppingEntity);
    } catch (ToppingNotFoundException e) {
      log.error("Topping not found with Id: " + toppingId);
      throw e;
    } catch (Exception e) {
      log.error("Exception occurs while updating topping {} : {}", topping, e.getLocalizedMessage(),
          e);
      throw new InternalServerException("Topping could not be updated: " + e.getLocalizedMessage());
    }
  }

  @DeleteMapping("/drinks/{drinkId}")
  public ResponseEntity<Void> deleteDrink(@PathVariable("drinkId") Long drinkId) {
    try {
      drinkService.deleteDrink(drinkId);
    } catch (Exception e) {
      log.error("Exception occurs while deleting drink {} : {}", drinkId, e.getLocalizedMessage(),
          e);
      throw new InternalServerException("Drink could not be deleted: " + e.getLocalizedMessage());
    }
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/toppings/{toppingId}")
  public ResponseEntity<Void> deleteTopping(@PathVariable("toppingId") Long toppingId) {
    try {
      toppingService.deleteTopping(toppingId);
    } catch (Exception e) {
      log.error("Exception occurs while deleting topping {} : {}", toppingId,
          e.getLocalizedMessage(), e);
      throw new InternalServerException("Topping could not be deleted: " + e.getLocalizedMessage());
    }
    return ResponseEntity.ok().build();
  }
}

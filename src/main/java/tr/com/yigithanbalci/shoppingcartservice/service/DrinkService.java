package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;

public interface DrinkService {

  void createDrink(DrinkEntity drinkEntity);
  void updateDrink(DrinkEntity drinkEntity);
  void deleteDrink(DrinkEntity drinkEntity);
  List<Drink> findAll();
}

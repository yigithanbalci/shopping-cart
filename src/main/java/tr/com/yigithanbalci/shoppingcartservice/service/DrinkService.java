package tr.com.yigithanbalci.shoppingcartservice.service;

import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;

public interface DrinkService {

  void createDrink(DrinkEntity drinkEntity);
  void updateDrink(DrinkEntity drinkEntity);
  void deleteDrink(DrinkEntity drinkEntity);
}

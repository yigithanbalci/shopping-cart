package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;

public interface DrinkService {

  DrinkEntity createDrink(DrinkEntity drinkEntity);
  DrinkEntity updateDrink(DrinkEntity drinkEntity);
  void deleteDrink(Long id);
  List<DrinkEntity> findAll();
}

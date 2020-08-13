package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;

public interface DrinkService {

  DrinkEntity create(DrinkEntity drinkEntity);
  DrinkEntity update(DrinkEntity drinkEntity);
  void delete(Long id);
  List<DrinkEntity> findAll();
}

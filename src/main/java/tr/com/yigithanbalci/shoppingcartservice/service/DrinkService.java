package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.DrinkInput;

public interface DrinkService {

  Drink create(DrinkInput drinkInput);
  Drink update(Drink drink);
  void delete(Long id);
  Drink findById(Long id);
  List<Drink> findAll();
}

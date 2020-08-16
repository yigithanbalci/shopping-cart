package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import lombok.NonNull;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.DrinkInput;

public interface DrinkService {

  Drink create(@NonNull final DrinkInput drinkInput);
  Drink update(@NonNull final Drink drink);
  void delete(@NonNull final Long id);
  Drink findById(@NonNull final Long id);
  List<Drink> findAll();
}

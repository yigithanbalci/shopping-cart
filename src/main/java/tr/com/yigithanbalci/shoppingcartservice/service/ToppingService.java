package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import lombok.NonNull;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.dto.ToppingInput;

public interface ToppingService {

  Topping create(@NonNull final ToppingInput toppingInput);
  Topping update(@NonNull final Topping topping);
  void delete(@NonNull final Long id);
  Topping findById(@NonNull final Long id);
  List<Topping> findAll();
}

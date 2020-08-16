package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.dto.ToppingInput;

public interface ToppingService {

  Topping create(ToppingInput toppingInput);
  Topping update(Topping topping);
  void delete(Long id);
  Topping findById(Long id);
  List<Topping> findAll();
}

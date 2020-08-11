package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;

public interface ToppingService {

  void createTopping(ToppingEntity toppingEntity);
  void updateTopping(ToppingEntity toppingEntity);
  void deleteTopping(ToppingEntity toppingEntity);
  List<Topping> findAll();
}

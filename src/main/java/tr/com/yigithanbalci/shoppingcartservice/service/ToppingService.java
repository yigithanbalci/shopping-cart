package tr.com.yigithanbalci.shoppingcartservice.service;

import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;

public interface ToppingService {

  void createTopping(ToppingEntity toppingEntity);
  void updateTopping(ToppingEntity toppingEntity);
  void deleteTopping(ToppingEntity toppingEntity);
}

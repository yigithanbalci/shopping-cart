package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;

public interface ToppingService {

  // TODO: 13.08.2020 javadoc.
  ToppingEntity createTopping(ToppingEntity toppingEntity);
  ToppingEntity updateTopping(ToppingEntity toppingEntity);
  void deleteTopping(Long id);
  List<ToppingEntity> findAll();
}

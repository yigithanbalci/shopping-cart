package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;

public interface ToppingService {

  ToppingEntity create(ToppingEntity toppingEntity);
  ToppingEntity update(ToppingEntity toppingEntity);
  void delete(Long id);
  List<ToppingEntity> findAll();
}

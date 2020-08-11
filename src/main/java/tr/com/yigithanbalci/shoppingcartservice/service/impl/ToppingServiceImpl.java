package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.ToppingRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToppingServiceImpl implements ToppingService {

  private final ToppingRepository repository;

  @Override
  public void createTopping(ToppingEntity toppingEntity) {

  }

  @Override
  public void updateTopping(ToppingEntity toppingEntity) {

  }

  @Override
  public void deleteTopping(ToppingEntity toppingEntity) {

  }
}

package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.ToppingRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToppingServiceImpl implements ToppingService {

  @NonNull private final ToppingRepository repository;

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public void createTopping(ToppingEntity toppingEntity) {

  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public void updateTopping(ToppingEntity toppingEntity) {

  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public void deleteTopping(ToppingEntity toppingEntity) {

  }

  @Override
  public List<Topping> findAll() {
    return null;
  }
}

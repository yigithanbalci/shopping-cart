package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {

  @NonNull private final DrinkRepository repository;

  @Override
  public void createDrink(DrinkEntity drinkEntity) {

  }

  @Override
  public void updateDrink(DrinkEntity drinkEntity) {

  }

  @Override
  public void deleteDrink(DrinkEntity drinkEntity) {

  }

  @Override
  public List<Drink> findAll() {
    return null;
  }
}

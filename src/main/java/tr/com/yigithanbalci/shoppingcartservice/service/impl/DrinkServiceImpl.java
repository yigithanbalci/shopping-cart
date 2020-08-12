package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.exception.DrinkNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {

  @NonNull
  private final DrinkRepository repository;

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public DrinkEntity createDrink(DrinkEntity drinkEntity) {
    log.info("Creating a drink: " + drinkEntity.getName());
    drinkEntity.setId(null);
    DrinkEntity createdDrink = repository.save(drinkEntity);
    log.info("Created a drink: " + drinkEntity.getName());
    return createdDrink;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public DrinkEntity updateDrink(DrinkEntity drinkEntity) {
    log.info("Updating a drink: " + drinkEntity.getName());
    DrinkEntity retrieved = repository.findById(drinkEntity.getId())
        .orElseThrow(() -> new DrinkNotFoundException("Drink not found with id: " + drinkEntity.getId()));
    retrieved.setName(drinkEntity.getName());
    retrieved.setPrice(drinkEntity.getPrice());
    DrinkEntity updatedDrink = repository.save(drinkEntity);
    log.info("Updated a drink: " + drinkEntity.getName());
    return updatedDrink;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public void deleteDrink(Long id) {
    log.info("Deleting a drink: " + id);
    repository.deleteById(id);
    log.info("Deleted a drink: " + id);
  }

  @Override
  public List<DrinkEntity> findAll() {
    log.info("Retrieving all drinks");
    List<DrinkEntity> all = repository.findAll();
    if (all.isEmpty()){
      throw new DrinkNotFoundException("Not found any drinks in database.");
    }
    log.info("Retrieved all drink");
    return all;
  }
}

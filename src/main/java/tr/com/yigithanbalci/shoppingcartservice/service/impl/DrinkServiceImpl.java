package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.DrinkInput;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {

  private final DrinkRepository repository;

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public Drink create(DrinkInput drinkInput) {
    log.debug("Creating a drink: " + drinkInput.getName());
    DrinkEntity createdDrink = repository.save(DrinkEntity
        .createWithNameAndPrice(drinkInput.getName(), drinkInput.getAmount()));
    log.debug("Created a drink: " + drinkInput.getName());
    return Drink.from(createdDrink);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public Drink update(@NonNull Drink drink) {
    log.debug("Updating a drink: " + drink.getName());
    DrinkEntity retrievedDrink = repository.findById(drink.getId())
        .orElseThrow(
            () -> new EntityNotFoundException("Drink not found with id: " + drink.getId()));
    retrievedDrink.setName(drink.getName());
    retrievedDrink.setAmount(drink.getAmount());
    DrinkEntity updatedDrink = repository.save(retrievedDrink);
    log.debug("Updated a drink: " + drink.getName());
    return Drink.from(updatedDrink);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public void delete(@NonNull Long id) {
    log.debug("Deleting a drink: " + id);
    repository.deleteById(id);
    log.debug("Deleted a drink: " + id);
  }

  @Override
  public Drink findById(@NonNull Long id) {
    return Drink.from(repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Drink not found with id: " + id)));
  }

  @Override
  public List<Drink> findAll() {
    log.debug("Retrieving all drinks");
    List<DrinkEntity> all = repository.findAll();
    log.debug("Retrieved all drink");
    return all.stream().map(Drink::from).collect(Collectors.toList());
  }
}

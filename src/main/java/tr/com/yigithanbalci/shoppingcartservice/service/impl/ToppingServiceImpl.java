package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.dto.ToppingInput;
import tr.com.yigithanbalci.shoppingcartservice.exception.ToppingNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.ToppingRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToppingServiceImpl implements ToppingService {

  private final ToppingRepository repository;

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public Topping create(ToppingInput toppingInput) {
    log.debug("Creating a topping: " + toppingInput.getName());
    ToppingEntity createdTopping = repository.save(ToppingEntity
        .createWithNameAndPrice(toppingInput.getName(), toppingInput.getAmount()));
    log.debug("Created a topping: " + toppingInput.getName());
    return Topping.from(createdTopping);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public Topping update(Topping topping) {
    log.debug("Updating a topping: " + topping.getName());
    ToppingEntity updatedTopping = repository.findById(topping.getId())
        .orElseThrow(() -> new ToppingNotFoundException(
            "Topping not found with id: " + topping.getId()));
    updatedTopping.setName(topping.getName());
    updatedTopping.setAmount(topping.getAmount());
    updatedTopping = repository.save(updatedTopping);
    log.debug("Updated a topping: " + updatedTopping.getName());
    return Topping.from(updatedTopping);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public void delete(Long id) {
    log.debug("Deleting a topping: " + id);
    repository.deleteById(id);
    log.debug("Deleted a topping: " + id);
  }

  @Override
  public Topping findById(Long id) {
    return Topping.from(repository.findById(id)
        .orElseThrow(() -> new ToppingNotFoundException("Drink not found with id: " + id)));
  }

  @Override
  public List<Topping> findAll() {
    log.debug("Retrieving all topping");
    List<ToppingEntity> all = repository.findAll();
    if (all.isEmpty()) {
      throw new ToppingNotFoundException("Not found any toppings in database.");
    }
    log.debug("Retrieved all topping");
    return all.stream().map(Topping::from).collect(Collectors.toList());
  }
}

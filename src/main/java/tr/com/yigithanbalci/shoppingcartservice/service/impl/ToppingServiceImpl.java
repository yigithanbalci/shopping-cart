package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.exception.ToppingNotFoundException;
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
  public ToppingEntity createTopping(ToppingEntity toppingEntity) {
    log.info("Creating a topping: " + toppingEntity.getName());
    toppingEntity.setId(null);
    ToppingEntity createdTopping = repository.save(toppingEntity);
    log.info("Created a topping: " + toppingEntity.getName());
    return createdTopping;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public ToppingEntity updateTopping(ToppingEntity toppingEntity) {
    log.info("Updating a topping: " + toppingEntity.getName());
    ToppingEntity retrieved = repository.findById(toppingEntity.getId())
        .orElseThrow(() -> new ToppingNotFoundException("Topping not found with id: " + toppingEntity.getId()));
    retrieved.setName(toppingEntity.getName());
    retrieved.setPrice(toppingEntity.getPrice());
    ToppingEntity updatedTopping = repository.save(toppingEntity);
    log.info("Updated a topping: " + toppingEntity.getName());
    return updatedTopping;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public void deleteTopping(Long id) {
    log.info("Deleting a topping: " + id);
    repository.deleteById(id);
    log.info("Deleted a topping: " + id);
  }

  @Override
  public List<ToppingEntity> findAll() {
    log.info("Retrieving all topping");
    List<ToppingEntity> all = repository.findAll();
    if (all.isEmpty()){
      throw new ToppingNotFoundException("Not found any toppings in database.");
    }
    log.info("Retrieved all topping");
    return all;
  }
}

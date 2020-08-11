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
  public void createTopping(ToppingEntity toppingEntity) {
    log.info("Creating a topping: " + toppingEntity.getName());
    toppingEntity.setId(null);
    repository.save(toppingEntity);
    log.info("Created a topping: " + toppingEntity.getName());
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public void updateTopping(ToppingEntity toppingEntity) {
    log.info("Updating a topping: " + toppingEntity.getName());
    ToppingEntity retrieved = repository.findById(toppingEntity.getId())
        .orElseThrow(() -> new ToppingNotFoundException("Topping not found with id: " + toppingEntity.getId()));
    retrieved.setName(toppingEntity.getName());
    retrieved.setPrice(toppingEntity.getPrice());
    repository.save(toppingEntity);
    log.info("Updated a topping: " + toppingEntity.getName());
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public void deleteTopping(ToppingEntity toppingEntity) {
    log.info("Deleting a topping: " + toppingEntity.getName());
    repository.deleteById(toppingEntity.getId());
    log.info("Deleted a topping: " + toppingEntity.getName());
  }

  @Override
  public List<ToppingEntity> findAll() {
    log.info("Retrieving all topping");
    List<ToppingEntity> all = repository.findAll();
    log.info("Retrieved all topping");
    return all;
  }
}

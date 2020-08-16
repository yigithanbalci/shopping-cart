package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedTopping;
import tr.com.yigithanbalci.shoppingcartservice.model.Customer;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkToppingRelation;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.CustomerRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkToppingRelationRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.ToppingRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ReportService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final CustomerRepository customerRepository;
  private final DrinkRepository drinkRepository;
  private final ToppingRepository toppingRepository;
  private final DrinkToppingRelationRepository drinkToppingRelationRepository;

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public List<CustomerAnalysis> getCustomerAnalysisReport() {
    log.debug("Producing Customer Analysis...");
    List<Customer> customers = customerRepository.findAll();
    List<CustomerAnalysis> customerAnalysis = new ArrayList<>();
    customers.forEach(customer -> customerAnalysis.add(
        CustomerAnalysis.createWithUsernameAndTotalOrders(customer.getUser().getUsername(), customer.getTotalAmountOfOrders())));
    log.debug("Produced Customer Analysis.");
    return customerAnalysis;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public List<DrinkAndMostUsedTopping> getDrinkAnalysisReport() {
    log.debug("Producing Drink Analysis...");
    List<DrinkEntity> drinks = drinkRepository.findAll();

    List<DrinkAndMostUsedTopping> drinkAnalysis = new ArrayList<>();
    drinks.forEach(drinkEntity -> {
      DrinkToppingRelation topByDrinkIdEqualsOrderByAmount = drinkToppingRelationRepository
          .findTopByDrinkIdEqualsOrderByNumberOfUsageTogetherDesc(drinkEntity.getId());
      Optional.ofNullable(topByDrinkIdEqualsOrderByAmount).ifPresent(drinkToppingRelation -> drinkAnalysis.add(
          DrinkAndMostUsedTopping.createWithDrinkAndMostUsedTopping(drinkEntity.getName(),
              toppingRepository.findById(drinkToppingRelation.getToppingId()).orElse(
                  ToppingEntity.createWithNameAndPrice("none", BigDecimal.ZERO)).getName())));
    });
    log.debug("Produced Drink Analysis.");
    return drinkAnalysis;
  }
}

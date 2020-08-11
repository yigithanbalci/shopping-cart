package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAndOrderWrapper;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedToppingWrapper;
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

  @NonNull
  private final CustomerRepository customerRepository;

  @NonNull
  private final DrinkRepository drinkRepository;

  @NonNull
  private final ToppingRepository toppingRepository;

  @NonNull
  private final DrinkToppingRelationRepository drinkToppingRelationRepository;

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public CustomerAnalysis customerAnalysisReport() {
    log.info("Producing Customer Analysis...");
    List<Customer> customers = customerRepository.findAll();
    CustomerAnalysis customerAnalysis = new CustomerAnalysis();
    customers.forEach(customer -> customerAnalysis.addCustomerAndOrders(
        CustomerAndOrderWrapper.builder().username(customer.getUser().getUsername())
            .totalAmountOfOrders(customer.getTotalOrders()).build()));
    log.info("Produced Customer Analysis.");
    return customerAnalysis;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public DrinkAnalysis drinkAnalysisReport() {
    log.info("Producing Drink Analysis...");
    List<DrinkEntity> drinks = drinkRepository.findAll();

    DrinkAnalysis drinkAnalysis = new DrinkAnalysis();
    drinks.forEach(drinkEntity -> {
      DrinkToppingRelation topByDrinkIdEqualsOrderByAmount = drinkToppingRelationRepository
          .findTopByDrinkIdEqualsOrderByAmountDesc(drinkEntity.getId());
      drinkAnalysis.addDrinksAndToppings(
          DrinkAndMostUsedToppingWrapper.builder().drink(drinkEntity.getName()).mostUsedTopping(
              toppingRepository.findById(topByDrinkIdEqualsOrderByAmount.getToppingId()).orElse(
                  ToppingEntity.builder().name("none").build()).getName()).build());
    });
    log.info("Produced Drink Analysis.");
    return drinkAnalysis;
  }
}

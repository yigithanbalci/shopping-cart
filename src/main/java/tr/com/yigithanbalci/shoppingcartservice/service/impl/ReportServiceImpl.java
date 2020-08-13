package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.ArrayList;
import java.util.List;
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
    log.info("Producing Customer Analysis...");
    List<Customer> customers = customerRepository.findAll();
    List<CustomerAnalysis> customerAnalysis = new ArrayList<>();
    customers.forEach(customer -> customerAnalysis.add(
        CustomerAnalysis.builder().username(customer.getUser().getUsername())
            .totalAmountOfOrders(customer.getTotalOrders()).build()));
    log.info("Produced Customer Analysis.");
    return customerAnalysis;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Override
  public List<DrinkAndMostUsedTopping> getDrinkAnalysisReport() {
    log.info("Producing Drink Analysis...");
    List<DrinkEntity> drinks = drinkRepository.findAll();

    List<DrinkAndMostUsedTopping> drinkAnalysis = new ArrayList<>();
    drinks.forEach(drinkEntity -> {
      DrinkToppingRelation topByDrinkIdEqualsOrderByAmount = drinkToppingRelationRepository
          .findTopByDrinkIdEqualsOrderByAmountDesc(drinkEntity.getId());
      drinkAnalysis.add(
          DrinkAndMostUsedTopping.builder().drink(drinkEntity.getName()).mostUsedTopping(
              toppingRepository.findById(topByDrinkIdEqualsOrderByAmount.getToppingId()).orElse(
                  ToppingEntity.builder().name("none").build()).getName()).build());
    });
    log.info("Produced Drink Analysis.");
    return drinkAnalysis;
  }
}

package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedTopping;
import tr.com.yigithanbalci.shoppingcartservice.model.Customer;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkToppingRelation;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.User;
import tr.com.yigithanbalci.shoppingcartservice.repository.CustomerRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkToppingRelationRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.ToppingRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ReportService;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.ReportServiceImpl;

@ExtendWith(SpringExtension.class)
public class ReportServiceTests {

  private ReportService reportService;

  @MockBean
  private CustomerRepository customerRepository;

  @MockBean
  private DrinkRepository drinkRepository;

  @MockBean
  private ToppingRepository toppingRepository;

  @MockBean
  private DrinkToppingRelationRepository drinkToppingRelationRepository;

  @BeforeEach
  public void setUp() {
    Customer josh = Customer.builder().id(1L).user(User.builder().id(1L).username("josh").build())
        .totalOrders(5L)
        .build();
    Customer jack = Customer.builder().id(2L).user(User.builder().id(2L).username("josh").build())
        .totalOrders(8L)
        .build();
    Customer jonathan = Customer.builder().id(3L)
        .user(User.builder().id(3L).username("josh").build())
        .totalOrders(9L).build();

    List<Customer> customers = new ArrayList<>();
    customers.add(josh);
    customers.add(jack);
    customers.add(jonathan);

    Mockito.when(customerRepository.findAll()).thenReturn(customers);

    DrinkEntity blackCoffee = DrinkEntity.builder().id(1L).name("Black Coffee").price(4.0f).build();
    DrinkEntity latte = DrinkEntity.builder().id(2L).name("Latte").price(5.0f).build();

    List<DrinkEntity> drinks = new ArrayList<>();
    drinks.add(blackCoffee);
    drinks.add(latte);

    Mockito.when(drinkRepository.findAll()).thenReturn(drinks);

    ToppingEntity milk = ToppingEntity.builder().id(1L).name("Milk").price(2.0f).build();
    ToppingEntity hazelnutSyrup = ToppingEntity.builder().id(2L).name("Hazelnut syrup").price(3.0f)
        .build();

    Mockito.when(toppingRepository.findById(1L)).thenReturn(Optional.of(milk));
    Mockito.when(toppingRepository.findById(2L)).thenReturn(Optional.of(hazelnutSyrup));

    DrinkToppingRelation blackCoffeeMilkRelation = new DrinkToppingRelation();
    blackCoffeeMilkRelation.setDrinkId(1L);
    blackCoffeeMilkRelation.setToppingId(1L);
    blackCoffeeMilkRelation.setAmount(5L);
    blackCoffeeMilkRelation.setId(1L);

    DrinkToppingRelation blackCoffeeHazelnutSyrupRelation = new DrinkToppingRelation();
    blackCoffeeHazelnutSyrupRelation.setDrinkId(1L);
    blackCoffeeHazelnutSyrupRelation.setToppingId(2L);
    blackCoffeeHazelnutSyrupRelation.setAmount(7L);
    blackCoffeeHazelnutSyrupRelation.setId(2L);

    DrinkToppingRelation latteRelation = new DrinkToppingRelation();
    latteRelation.setDrinkId(2L);
    latteRelation.setToppingId(2L);
    latteRelation.setAmount(3L);
    latteRelation.setId(3L);

    Mockito.when(drinkToppingRelationRepository.findTopByDrinkIdEqualsOrderByAmountDesc(1L))
        .thenReturn(blackCoffeeHazelnutSyrupRelation);
    Mockito.when(drinkToppingRelationRepository.findTopByDrinkIdEqualsOrderByAmountDesc(2L))
        .thenReturn(latteRelation);

    reportService = new ReportServiceImpl(customerRepository, drinkRepository, toppingRepository,
        drinkToppingRelationRepository);
  }

  @Test
  public void customerAnalysisReportTest() {
    String username = "josh";
    Long orders = 5L;

    List<CustomerAnalysis> found = reportService.getCustomerAnalysisReport();
    CustomerAnalysis foundAnalysisOfCustomer = found.stream()
        .filter(customerAndOrderWrapper -> "josh".equals(customerAndOrderWrapper.getUsername()))
        .findFirst().orElse(
            CustomerAnalysis.builder().username("jennifer").totalAmountOfOrders(15L)
                .build());

    assertThat(found.size()).isEqualTo(3);
    assertThat(foundAnalysisOfCustomer.getUsername()).isEqualTo(username);
    assertThat(foundAnalysisOfCustomer.getTotalAmountOfOrders()).isEqualTo(orders);
  }

  @Test
  public void drinkAnalysisReportTest() {
    String drink = "Black Coffee";
    String topping = "Hazelnut syrup";

    List<DrinkAndMostUsedTopping> drinkAnalysis = reportService.getDrinkAnalysisReport();
    DrinkAndMostUsedTopping found = drinkAnalysis.stream()
        .filter(drinkAndMostUsedToppingWrapper -> "Black Coffee"
            .equals(drinkAndMostUsedToppingWrapper.getDrink())).findFirst().orElse(
            DrinkAndMostUsedTopping.builder().drink("Macchiato").mostUsedTopping("Lemoon")
                .build());

    assertThat(drinkAnalysis.size()).isEqualTo(2);
    assertThat(found.getDrink()).isEqualTo(drink);
    assertThat(found.getMostUsedTopping()).isEqualTo(topping);
  }

}
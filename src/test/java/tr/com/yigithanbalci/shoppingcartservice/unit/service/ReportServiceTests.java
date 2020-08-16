package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
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
    Customer josh = Customer.createWithUserAndTotalOrders(User.builder().id(1L).username("josh").build(), BigDecimal.valueOf(25.0));
    Customer jack = Customer.createWithUserAndTotalOrders(User.builder().id(2L).username("josh").build(), BigDecimal.valueOf(20.0));
    Customer jonathan = Customer.createWithUserAndTotalOrders(User.builder().id(3L).username("josh").build(), BigDecimal.valueOf(15.0));

    List<Customer> customers = new ArrayList<>();
    customers.add(josh);
    customers.add(jack);
    customers.add(jonathan);

    Mockito.when(customerRepository.findAll()).thenReturn(customers);

    DrinkEntity blackCoffee =new DrinkEntity(1L, "Black Coffee", BigDecimal.valueOf(4.0));
    DrinkEntity latte = new DrinkEntity(2L, "Latte", BigDecimal.valueOf(5.0));

    List<DrinkEntity> drinks = new ArrayList<>();
    drinks.add(blackCoffee);
    drinks.add(latte);

    Mockito.when(drinkRepository.findAll()).thenReturn(drinks);

    ToppingEntity milk = new ToppingEntity(1L, "Milk", BigDecimal.valueOf(2.0));
    ToppingEntity hazelnutSyrup = new ToppingEntity(2L, "Hazelnut syrup", BigDecimal.valueOf(3.0));

    Mockito.when(toppingRepository.findById(1L)).thenReturn(Optional.of(milk));
    Mockito.when(toppingRepository.findById(2L)).thenReturn(Optional.of(hazelnutSyrup));

    DrinkToppingRelation blackCoffeeMilkRelation = new DrinkToppingRelation();
    blackCoffeeMilkRelation.setDrinkId(1L);
    blackCoffeeMilkRelation.setToppingId(1L);
    blackCoffeeMilkRelation.setNumberOfUsageTogether(5L);
    blackCoffeeMilkRelation.setId(1L);

    DrinkToppingRelation blackCoffeeHazelnutSyrupRelation = new DrinkToppingRelation();
    blackCoffeeHazelnutSyrupRelation.setDrinkId(1L);
    blackCoffeeHazelnutSyrupRelation.setToppingId(2L);
    blackCoffeeHazelnutSyrupRelation.setNumberOfUsageTogether(7L);
    blackCoffeeHazelnutSyrupRelation.setId(2L);

    DrinkToppingRelation latteRelation = new DrinkToppingRelation();
    latteRelation.setDrinkId(2L);
    latteRelation.setToppingId(2L);
    latteRelation.setNumberOfUsageTogether(3L);
    latteRelation.setId(3L);

    Mockito.when(drinkToppingRelationRepository.findTopByDrinkIdEqualsOrderByNumberOfUsageTogetherDesc(1L))
        .thenReturn(blackCoffeeHazelnutSyrupRelation);
    Mockito.when(drinkToppingRelationRepository.findTopByDrinkIdEqualsOrderByNumberOfUsageTogetherDesc(2L))
        .thenReturn(latteRelation);

    reportService = new ReportServiceImpl(customerRepository, drinkRepository, toppingRepository,
        drinkToppingRelationRepository);
  }

  @Test
  public void customerAnalysisReportTest() {
    String username = "josh";
    BigDecimal orders = BigDecimal.valueOf(25.0);

    List<CustomerAnalysis> found = reportService.getCustomerAnalysisReport();
    CustomerAnalysis foundAnalysisOfCustomer = found.stream()
        .filter(customerAndOrderWrapper -> "josh".equals(customerAndOrderWrapper.getUsername()))
        .findFirst().orElse(
            CustomerAnalysis.createWithUsernameAndTotalOrders("jennifer", BigDecimal.valueOf(15.0)));

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
            DrinkAndMostUsedTopping.createWithDrinkAndMostUsedTopping("Macchiato", "Lemoon"));

    assertThat(drinkAnalysis.size()).isEqualTo(2);
    assertThat(found.getDrink()).isEqualTo(drink);
    assertThat(found.getMostUsedTopping()).isEqualTo(topping);
  }

}
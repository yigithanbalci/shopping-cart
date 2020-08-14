package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.model.Customer;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkToppingRelation;
import tr.com.yigithanbalci.shoppingcartservice.model.User;
import tr.com.yigithanbalci.shoppingcartservice.repository.CartRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.CustomerRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkToppingRelationRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.ShoppingServiceImpl;

@ExtendWith(SpringExtension.class)
public class ShoppingServiceTests {

  private ShoppingService shoppingService;

  @MockBean
  private CartRepository cartRepository;

  @MockBean
  private CustomerRepository customerRepository;

  @MockBean
  private DrinkToppingRelationRepository drinkToppingRelationRepository;

  @BeforeEach
  public void setUp() {
    Cart threeItemCart = new Cart();
    Cart twoItemCart = new Cart();
    Cart discountlessCart = new Cart();
    Cart twentyPerCentCart = new Cart();

    Drink java = Drink.builder().name("Java").price(7.0f).build();
    Drink latte = Drink.builder().name("Latte").price(5.0f).build();
    Drink mocha = Drink.builder().name("Mocha").price(8.0f).build();
    Drink blackCoffee = Drink.builder().name("Black Coffee").price(8.0f).build();

    Topping milk = Topping.builder().name("Milk").price(4.0f).build();
    Topping hazelnutSyrup = Topping.builder().name("Hazelnut syrup").price(3.0f)
        .build();

    Item latteItem = new Item(latte);
    latteItem.addTopping(hazelnutSyrup);

    Item mochaItem = new Item(mocha);
    mochaItem.addTopping(milk);

    Item blackCoffeeItem = new Item(blackCoffee);
    Item javaItem = new Item(java);

    threeItemCart.addItem(latteItem);
    threeItemCart.addItem(mochaItem);
    threeItemCart.addItem(blackCoffeeItem);

    twoItemCart.addItem(blackCoffeeItem);
    twoItemCart.addItem(mochaItem);

    discountlessCart.addItem(blackCoffeeItem);

    twentyPerCentCart.addItem(blackCoffeeItem);
    twentyPerCentCart.addItem(javaItem);

    User user1 = User.builder().id(1L).username("user1").password("user1").role("USER").enabled(true)
        .build();
    User user2 = User.builder().id(2L).username("user2").password("user2").role("USER").enabled(true)
        .build();
    User user3 = User.builder().id(3L).username("user3").password("user3").role("USER").enabled(true)
        .build();
    User user4 = User.builder().id(4L).username("user4").password("user4").role("USER").enabled(true)
        .build();

    Customer customer1 = new Customer();
    customer1.setId(1L);
    customer1.setTotalOrders(0L);
    Customer customer2 = new Customer();
    customer2.setId(2L);
    customer2.setTotalOrders(0L);
    Customer customer3 = new Customer();
    customer3.setId(3L);
    customer3.setTotalOrders(0L);
    Customer customer4 = new Customer();
    customer4.setId(4L);
    customer4.setTotalOrders(0L);

    user1.setCustomerId(customer1.getId());
    customer1.setUserId(user1.getId());
    user2.setCustomerId(customer2.getId());
    customer2.setUserId(user2.getId());
    user3.setCustomerId(customer3.getId());
    customer3.setUserId(user3.getId());
    user4.setCustomerId(customer4.getId());
    customer4.setUserId(user4.getId());

    Mockito.when(drinkToppingRelationRepository
        .findByDrinkIdEqualsAndToppingIdEquals(latte.getId(), hazelnutSyrup.getId()))
        .thenReturn(DrinkToppingRelation.builder().id(1L).drinkId(latte.getId()).toppingId(
            hazelnutSyrup.getId()).amount(1L).build());

    Mockito.when(drinkToppingRelationRepository
        .findByDrinkIdEqualsAndToppingIdEquals(mocha.getId(), milk.getId()))
        .thenReturn(null);

    Mockito.when(cartRepository.findByUserId(1L)).thenReturn(threeItemCart);
    Mockito.when(cartRepository.findByUserId(2L)).thenReturn(twoItemCart);
    Mockito.when(cartRepository.findByUserId(3L)).thenReturn(discountlessCart);
    Mockito.when(cartRepository.findByUserId(4L)).thenReturn(twentyPerCentCart);

    Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
    Mockito.when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));
    Mockito.when(customerRepository.findById(3L)).thenReturn(Optional.of(customer3));
    Mockito.when(customerRepository.findById(4L)).thenReturn(Optional.of(customer4));
    shoppingService = new ShoppingServiceImpl(cartRepository, customerRepository, drinkToppingRelationRepository);
  }

  @Test
  public void addItemTest() {
    Drink tea = Drink.builder().name("Tea").price(3.0f).build();
    Drink smootie = Drink.builder().name("Smootie").price(3.0f).build();

    Item teaItem = new Item(tea);
    Item smootieItem = new Item(smootie);

    Topping milk = Topping.builder().name("Milk").price(2.0f).build();
    teaItem.addTopping(milk);

    Cart found = shoppingService.addItemToCart(teaItem, 1L);
    Item foundItem = found.getItems().stream().filter(item -> item.getDrink().equals(tea))
        .findFirst()
        .orElse(smootieItem);

    assertThat(found.getItems().size()).isEqualTo(4);
    assertThat(foundItem.getDrink().getName()).isEqualTo("Tea");
    assertThat(foundItem.getToppings()).isEqualTo(teaItem.getToppings());
  }

  @Test
  public void deleteItemTest() {
    Drink blackCoffee = Drink.builder().name("Black Coffee").price(8.0f).build();
    Drink smootie = Drink.builder().name("Smootie").price(3.0f).build();

    Item smootieItem = new Item(smootie);
    Item blackCoffeeItem = new Item(blackCoffee);

    Cart found = shoppingService.deleteItemFromCart(blackCoffeeItem, 2L);
    Item foundItem = found.getItems().stream().filter(item -> item.getDrink().equals(blackCoffee))
        .findFirst()
        .orElse(smootieItem);

    assertThat(found.getItems().size()).isEqualTo(1);
    assertThat(foundItem.getDrink().getName()).isEqualTo("Smootie");
  }

  @Test
  public void whenCustomerNotFound_thenNotFound() {
    FinalizedCart discountlessFinalizedCart = shoppingService.checkoutCart(3L);

    float blackCoffee = 8.0f;

    assertThat(discountlessFinalizedCart.getOriginalAmount()).isEqualTo(blackCoffee);
    assertThat(discountlessFinalizedCart.getOriginalAmount()).isEqualTo(blackCoffee);
  }

  @Test
  public void whenMoreThanTwelveEuros_then25PerCentDiscount() {
    FinalizedCart twentyPerCentFinalizedCart = shoppingService.checkoutCart(4L);

    float blackCoffee = 8.0f;
    float javaItem = 7.0f;

    float twentyPerCentCart = blackCoffee + javaItem;
    assertThat(twentyPerCentFinalizedCart.getOriginalAmount()).isEqualTo(twentyPerCentCart);

    float discountedtwentyPerCentCart = twentyPerCentCart * 0.75f;
    assertThat(twentyPerCentFinalizedCart.getDiscountedAmount())
        .isEqualTo(discountedtwentyPerCentCart);
  }

  @Test
  public void checkoutTest() {
    FinalizedCart threeItemFinalizedCart = shoppingService.checkoutCart(1L);
    FinalizedCart twoItemFinalizedCart = shoppingService.checkoutCart(2L);

    float latteItem = 8.0f;
    float mochaItem = 12.0f;
    float blackCoffee = 8.0f;

    float threeItemCart = latteItem + mochaItem + blackCoffee;
    float twoItemCart = mochaItem + blackCoffee;

    assertThat(threeItemFinalizedCart.getOriginalAmount()).isEqualTo(threeItemCart);
    assertThat(twoItemFinalizedCart.getOriginalAmount()).isEqualTo(twoItemCart);

    float discountedThreeItemCart = Math.min((threeItemCart * 0.75f), (threeItemCart - 8.0f));
    float discountedTwoItemCart = twoItemCart * 0.75f;

    assertThat(threeItemFinalizedCart.getDiscountedAmount()).isEqualTo(discountedThreeItemCart);
    assertThat(twoItemFinalizedCart.getDiscountedAmount()).isEqualTo(discountedTwoItemCart);
  }
}

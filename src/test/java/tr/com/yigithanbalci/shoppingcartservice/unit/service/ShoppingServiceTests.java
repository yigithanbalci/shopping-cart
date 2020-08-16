package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;
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
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.model.Customer;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkToppingRelation;
import tr.com.yigithanbalci.shoppingcartservice.model.User;
import tr.com.yigithanbalci.shoppingcartservice.repository.CartRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.CustomerRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkToppingRelationRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ItemService;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.ShoppingServiceImpl;

@ExtendWith(SpringExtension.class)
public class ShoppingServiceTests {

  private ShoppingService shoppingService;

  @MockBean
  private ItemService itemService;

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

    Drink java = Drink.createWithIdAndNameAndPrice(5L, "Java", BigDecimal.valueOf(7.0));
    Drink latte = Drink.createWithIdAndNameAndPrice(6L, "Latte", BigDecimal.valueOf(5.0));
    Drink mocha = Drink.createWithIdAndNameAndPrice(7L, "Mocha", BigDecimal.valueOf(8.0));
    Drink blackCoffee = Drink
        .createWithIdAndNameAndPrice(8L, "Black Coffee", BigDecimal.valueOf(8.0));

    Topping milk = Topping.createWithIdAndNameAndPrice(5L, "Milk", BigDecimal.valueOf(4.0));
    Topping hazelnutSyrup = Topping
        .createWithIdAndNameAndPrice(6L, "Hazelnut syrup", BigDecimal.valueOf(3.0));

    Item latteItem = Item.createWithDrink(latte);
    latteItem.addTopping(hazelnutSyrup);

    Item mochaItem = Item.createWithDrink(mocha);
    mochaItem.addTopping(milk);

    Item blackCoffeeItem = Item.createWithDrink(blackCoffee);
    Item javaItem = Item.createWithDrink(java);

    threeItemCart.addItem(latteItem);
    threeItemCart.addItem(mochaItem);
    threeItemCart.addItem(blackCoffeeItem);

    twoItemCart.addItem(blackCoffeeItem);
    twoItemCart.addItem(mochaItem);

    discountlessCart.addItem(blackCoffeeItem);

    twentyPerCentCart.addItem(blackCoffeeItem);
    twentyPerCentCart.addItem(javaItem);

    User user1 = User.builder().id(1L).username("user1").password("user1").role("USER")
        .enabled(true).build();
    User user2 = User.builder().id(2L).username("user2").password("user2").role("USER")
        .enabled(true).build();
    User user3 = User.builder().id(3L).username("user3").password("user3").role("USER")
        .enabled(true).build();
    User user4 = User.builder().id(4L).username("user4").password("user4").role("USER")
        .enabled(true).build();

    Customer customer1 = new Customer();
    customer1.setTotalAmountOfOrders(BigDecimal.valueOf(0.0));
    Customer customer2 = new Customer();
    customer2.setTotalAmountOfOrders(BigDecimal.valueOf(0.0));
    Customer customer3 = new Customer();
    customer3.setTotalAmountOfOrders(BigDecimal.valueOf(0.0));
    Customer customer4 = new Customer();
    customer4.setTotalAmountOfOrders(BigDecimal.valueOf(0.0));

    user1.setCustomer(customer1);
    customer1.setUser(user1);
    user2.setCustomer(customer2);
    customer2.setUser(user2);
    user3.setCustomer(customer3);
    customer3.setUser(user3);
    user4.setCustomer(customer4);
    customer4.setUser(user4);

    Mockito.when(drinkToppingRelationRepository
        .findByDrinkIdEqualsAndToppingIdEquals(latte.getId(), hazelnutSyrup.getId()))
        .thenReturn(new DrinkToppingRelation(1L, latte.getId(), hazelnutSyrup.getId(), 1L));

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
    shoppingService = new ShoppingServiceImpl(itemService, cartRepository, customerRepository,
        drinkToppingRelationRepository);
  }

  @Test
  public void addItemTest() {
    Drink tea = Drink.createWithIdAndNameAndPrice(9L, "Tea", BigDecimal.valueOf(3.0));
    Drink smootie = Drink.createWithIdAndNameAndPrice(10L, "Smootie", BigDecimal.valueOf(3.0));

    Item teaItem = Item.createWithDrink(tea);
    Item smootieItem = Item.createWithDrink(smootie);

    Topping milk = Topping.createWithIdAndNameAndPrice(5L, "Milk", BigDecimal.valueOf(4.0));
    teaItem.addTopping(milk);

    ItemInput teaItemInput = new ItemInput(teaItem.getDrink().getId(),
        teaItem.getToppings().stream().map(Topping::getId).collect(
            Collectors.toList()));

    Mockito.when(itemService.getItemFromItemInput(teaItemInput))
        .thenReturn(teaItem);

    Cart found = shoppingService.addItemToCart(teaItemInput, 1L);
    Item foundItem = found.getItems().stream().filter(item -> item.getDrink().equals(tea))
        .findFirst()
        .orElse(smootieItem);

    assertThat(found.getItems().size()).isEqualTo(4);
    assertThat(foundItem.getDrink().getName()).isEqualTo("Tea");
    assertThat(foundItem.getToppings()).isEqualTo(teaItem.getToppings());
  }

  @Test
  public void deleteItemTest() {
    Drink blackCoffee = Drink
        .createWithIdAndNameAndPrice(8L, "Black Coffee", BigDecimal.valueOf(8.0));
    Drink smootie = Drink.createWithIdAndNameAndPrice(10L, "Smootie", BigDecimal.valueOf(3.0));

    Item smootieItem = Item.createWithDrink(smootie);
    Item blackCoffeeItem = Item.createWithDrink(blackCoffee);

    ItemInput blackCoffeeItemInput = new ItemInput(blackCoffeeItem.getDrink().getId(),
        blackCoffeeItem.getToppings().stream().map(Topping::getId).collect(
            Collectors.toList()));

    Mockito.when(itemService.getItemFromItemInput(blackCoffeeItemInput))
        .thenReturn(blackCoffeeItem);

    Cart found = shoppingService.deleteItemFromCart(blackCoffeeItemInput, 2L);
    Item foundItem = found.getItems().stream().filter(item -> item.getDrink().equals(blackCoffee))
        .findFirst()
        .orElse(smootieItem);

    assertThat(found.getItems().size()).isEqualTo(1);
    assertThat(foundItem.getDrink().getName()).isEqualTo("Smootie");
  }

  @Test
  public void whenLessThanThreeItemAnd12Eu_thenDiscountless() {
    FinalizedCart discountlessFinalizedCart = shoppingService.checkoutCart(3L);

    BigDecimal blackCoffee = BigDecimal.valueOf(8.0);

    assertThat(discountlessFinalizedCart.getOriginalAmount()).isEqualTo(blackCoffee);
    assertThat(discountlessFinalizedCart.getDiscountedAmount()).isEqualTo(blackCoffee);
  }

  @Test
  public void whenMoreThanTwelveEuros_then25PerCentDiscount() {
    FinalizedCart twentyPerCentFinalizedCart = shoppingService.checkoutCart(4L);

    BigDecimal blackCoffee = BigDecimal.valueOf(8.0);
    BigDecimal javaItem = BigDecimal.valueOf(7.0);

    BigDecimal twentyPerCentCart = blackCoffee.add(javaItem);
    assertThat(twentyPerCentFinalizedCart.getOriginalAmount()).isEqualTo(twentyPerCentCart);

    BigDecimal discountedtwentyPerCentCart = twentyPerCentCart.multiply(BigDecimal.valueOf(0.75));
    assertThat(twentyPerCentFinalizedCart.getDiscountedAmount())
        .isEqualTo(discountedtwentyPerCentCart);
  }

  @Test
  public void checkoutTest() {
    FinalizedCart threeItemFinalizedCart = shoppingService.checkoutCart(1L);
    FinalizedCart twoItemFinalizedCart = shoppingService.checkoutCart(2L);

    BigDecimal latteItem = BigDecimal.valueOf(8.0);
    BigDecimal mochaItem = BigDecimal.valueOf(12.0);
    BigDecimal blackCoffee = BigDecimal.valueOf(8.0);

    BigDecimal threeItemCart = latteItem.add(mochaItem).add(blackCoffee);
    BigDecimal twoItemCart = mochaItem.add(blackCoffee);

    assertThat(threeItemFinalizedCart.getOriginalAmount()).isEqualTo(threeItemCart);
    assertThat(twoItemFinalizedCart.getOriginalAmount()).isEqualTo(twoItemCart);

    BigDecimal discountedThreeItemCart = threeItemCart.multiply(BigDecimal.valueOf(0.75))
        .min(threeItemCart.subtract(BigDecimal.valueOf(8.0)));
    BigDecimal discountedTwoItemCart = twoItemCart.multiply(BigDecimal.valueOf(0.75));

    assertThat(threeItemFinalizedCart.getDiscountedAmount()).isEqualTo(discountedThreeItemCart);
    assertThat(twoItemFinalizedCart.getDiscountedAmount()).isEqualTo(discountedTwoItemCart);
  }
}

package tr.com.yigithanbalci.shoppingcartservice.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedTopping;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@IfProfileValue(name = "spring.profiles.active", value = "dev")
@DirtiesContext
public class ReportsRestControllerIntegrationTests {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @LocalServerPort
  private int port;

  private static final String LOCAL_HOST = "http://localhost:";

  @Test
  public void testReports(){
    Drink blackCoffee = Drink
        .createWithIdAndNameAndPrice(1L, "Black Coffee", BigDecimal.valueOf(4.0));
    Drink latte = Drink.createWithIdAndNameAndPrice(2L, "Latte", BigDecimal.valueOf(5.0));
    Drink tea = Drink.createWithIdAndNameAndPrice(4L, "Tea", BigDecimal.valueOf(3.0));

    Topping milk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(2.0));
    Topping hazelnutSyrup = Topping
        .createWithIdAndNameAndPrice(2L, "Hazelnut syrup", BigDecimal.valueOf(3.0));
    Topping lemon = Topping.createWithIdAndNameAndPrice(4L, "Lemon", BigDecimal.valueOf(2.0));

    Item blackCoffeeItem = Item.createWithDrink(blackCoffee);
    blackCoffeeItem.addTopping(hazelnutSyrup);
    ItemInput blackCoffeeItemInput = new ItemInput(blackCoffeeItem.getDrink().getId(),
        blackCoffeeItem.getToppings().stream().map(Topping::getId).collect(
            Collectors.toList()));

    Item latteItem = Item.createWithDrink(latte);
    latteItem.addTopping(milk);
    ItemInput latteItemInput = new ItemInput(latteItem.getDrink().getId(),
        latteItem.getToppings().stream().map(Topping::getId).collect(
            Collectors.toList()));

    Item teaItem = Item.createWithDrink(tea);
    teaItem.addTopping(lemon);
    ItemInput teaItemInput = new ItemInput(teaItem.getDrink().getId(),
        teaItem.getToppings().stream().map(Topping::getId).collect(
            Collectors.toList()));

    Cart cart = testRestTemplate.withBasicAuth("user1", "user1")
        .postForObject(LOCAL_HOST + port + "/users/1/cart", blackCoffeeItemInput, Cart.class);

    assertThat(cart.getItems().size()).isEqualTo(1);
    assertThat(cart.getItems().get(0).getDrink().getId()).isEqualTo(blackCoffee.getId());

    cart = testRestTemplate.withBasicAuth("user1", "user1")
        .postForObject(LOCAL_HOST + port + "/users/1/cart", latteItemInput, Cart.class);

    assertThat(cart.getItems().size()).isEqualTo(2);
    assertThat(cart.getItems().get(1).getDrink().getId()).isEqualTo(latte.getId());

    cart = testRestTemplate.withBasicAuth("user1", "user1")
        .postForObject(LOCAL_HOST + port + "/users/1/cart", teaItemInput, Cart.class);

    assertThat(cart.getItems().size()).isEqualTo(3);
    assertThat(cart.getItems().get(2).getDrink().getId()).isEqualTo(tea.getId());

    ResponseEntity<FinalizedCart> finalizedCartResponseEntity = testRestTemplate.withBasicAuth("user1", "user1")
        .getForEntity(LOCAL_HOST + port + "/users/1/cart/checkout", FinalizedCart.class);

    List<CustomerAnalysis> customerAnalyses = Arrays.asList(testRestTemplate.withBasicAuth("admin", "admin")
        .getForObject(LOCAL_HOST + port + "/admin/reports/users/total-orders", CustomerAnalysis[].class).clone());

    List<DrinkAndMostUsedTopping> drinkAndMostUsedToppings = Arrays.asList(testRestTemplate.withBasicAuth("admin", "admin")
        .getForObject(LOCAL_HOST + port + "/admin/reports/drinks/most-used-topping", DrinkAndMostUsedTopping[].class).clone());

    assertThat(customerAnalyses.size()).isEqualTo(3);
    assertThat(customerAnalyses.get(0).getUsername()).isEqualTo("user1");
    assertThat(customerAnalyses.get(0).getTotalAmountOfOrders().doubleValue()).isEqualTo(
        Objects.requireNonNull(finalizedCartResponseEntity.getBody()).getDiscountedAmount().doubleValue());

    assertThat(drinkAndMostUsedToppings.size()).isEqualTo(3);
    assertThat(drinkAndMostUsedToppings.get(0).getDrink()).isEqualTo(blackCoffee.getName());
    assertThat(drinkAndMostUsedToppings.get(0).getMostUsedTopping()).isEqualTo(hazelnutSyrup.getName());

    assertThat(drinkAndMostUsedToppings.get(1).getDrink()).isEqualTo(latte.getName());
    assertThat(drinkAndMostUsedToppings.get(1).getMostUsedTopping()).isEqualTo(milk.getName());

    assertThat(drinkAndMostUsedToppings.get(2).getDrink()).isEqualTo(tea.getName());
    assertThat(drinkAndMostUsedToppings.get(2).getMostUsedTopping()).isEqualTo(lemon.getName());
  }
}
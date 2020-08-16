package tr.com.yigithanbalci.shoppingcartservice.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@IfProfileValue(name = "spring.profiles.active", value = "dev")
public class ProductsCustomerRestControllerIntegrationTests {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @LocalServerPort
  private int port;

  private static final String LOCAL_HOST = "http://localhost:";

  @Test
  public void testGetToppings(){
    Topping milk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(2.0));
    Topping hazelnutSyrup = Topping
        .createWithIdAndNameAndPrice(2L, "Hazelnut syrup", BigDecimal.valueOf(3.0));
    Topping chocolateSauce = Topping
        .createWithIdAndNameAndPrice(3L, "Chocolate sauce", BigDecimal.valueOf(5.0));
    Topping lemon = Topping.createWithIdAndNameAndPrice(4L, "Lemon", BigDecimal.valueOf(2.0));

    List<ToppingEntity> toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());

    assertThat(toppings.size()).isEqualTo(4);
    assertThat(toppings.get(0).getId()).isEqualTo(milk.getId());
    assertThat(toppings.get(1).getName()).isEqualTo(hazelnutSyrup.getName());
    assertThat(toppings.get(2).getAmount().doubleValue()).isEqualTo(chocolateSauce.getAmount().doubleValue());
    assertThat(toppings.get(3).getAmount().doubleValue()).isEqualTo(lemon.getAmount().doubleValue());
  }

  @Test
  public void testGetDrinks(){
    Drink blackCoffee = Drink
        .createWithIdAndNameAndPrice(1L, "Black Coffee", BigDecimal.valueOf(4.0));
    Drink latte = Drink.createWithIdAndNameAndPrice(2L, "Latte", BigDecimal.valueOf(5.0));
    Drink mocha =Drink.createWithIdAndNameAndPrice(3L, "Mocha", BigDecimal.valueOf(6.0));
    Drink tea = Drink.createWithIdAndNameAndPrice(4L, "Tea", BigDecimal.valueOf(3.0));

    List<DrinkEntity> drinks = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/drinks", DrinkEntity[].class).clone());

    assertThat(drinks.size()).isEqualTo(4);
    assertThat(drinks.get(0).getId()).isEqualTo(blackCoffee.getId());
    assertThat(drinks.get(1).getName()).isEqualTo(latte.getName());
    assertThat(drinks.get(2).getAmount().doubleValue()).isEqualTo(mocha.getAmount().doubleValue());
    assertThat(drinks.get(3).getAmount().doubleValue()).isEqualTo(tea.getAmount().doubleValue());
  }
}

package tr.com.yigithanbalci.shoppingcartservice.integration;

import static org.assertj.core.api.Assertions.assertThat;

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
    ToppingEntity milk = ToppingEntity.builder().id(1L).name("Milk").price(2.0f).build();
    ToppingEntity hazelnutSyrup = ToppingEntity.builder().id(2L).name("Hazelnut syrup").price(3.0f).build();
    ToppingEntity chocolateSauce = ToppingEntity.builder().id(3L).name("Chocolate sauce").price(5.0f).build();
    ToppingEntity lemon = ToppingEntity.builder().id(4L).name("Lemon").price(2.0f).build();

    List<ToppingEntity> toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());

    assertThat(toppings.size()).isEqualTo(4);
    assertThat(toppings.get(0).getId()).isEqualTo(milk.getId());
    assertThat(toppings.get(1).getName()).isEqualTo(hazelnutSyrup.getName());
    assertThat(toppings.get(2).getPrice()).isEqualTo(chocolateSauce.getPrice());
    assertThat(toppings.get(3).getPrice()).isEqualTo(lemon.getPrice());
  }

  @Test
  public void testGetDrinks(){
    DrinkEntity blackCoffee = DrinkEntity.builder().id(1L).name("Black Coffee").price(4.0f).build();
    DrinkEntity latte = DrinkEntity.builder().id(2L).name("Latte").price(5.0f).build();
    DrinkEntity mocha = DrinkEntity.builder().id(3L).name("Mocha").price(6.0f).build();
    DrinkEntity tea = DrinkEntity.builder().id(4L).name("Tea").price(3.0f).build();

    List<DrinkEntity> drinks = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/drinks", DrinkEntity[].class).clone());

    assertThat(drinks.size()).isEqualTo(4);
    assertThat(drinks.get(0).getId()).isEqualTo(blackCoffee.getId());
    assertThat(drinks.get(1).getName()).isEqualTo(latte.getName());
    assertThat(drinks.get(2).getPrice()).isEqualTo(mocha.getPrice());
    assertThat(drinks.get(3).getPrice()).isEqualTo(tea.getPrice());
  }
}

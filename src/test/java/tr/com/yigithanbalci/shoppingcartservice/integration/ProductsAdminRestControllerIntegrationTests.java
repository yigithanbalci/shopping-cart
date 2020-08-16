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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.DrinkInput;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.dto.ToppingInput;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@IfProfileValue(name = "spring.profiles.active", value = "dev")
@DirtiesContext
public class ProductsAdminRestControllerIntegrationTests {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @LocalServerPort
  private int port;

  private static final String LOCAL_HOST = "http://localhost:";

  @Test
  public void testCrudDrinks() {
    Drink greenTea = Drink.createWithIdAndNameAndPrice(5L, "Green Tea", BigDecimal.valueOf(3.0));
    Drink updatedGreenTea = Drink
        .createWithIdAndNameAndPrice(5L, "Green Tea", BigDecimal.valueOf(4.0));

    List<DrinkEntity> drinks = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/drinks", DrinkEntity[].class).clone());

    assertThat(drinks.size()).isEqualTo(4);

    ResponseEntity<Drink> drinkResponseEntity = testRestTemplate.withBasicAuth("admin", "admin")
        .postForEntity(LOCAL_HOST + port + "/admin/products/drinks",
            new DrinkInput(greenTea.getName(), greenTea.getAmount()), Drink.class);

    assertThat(Objects.requireNonNull(drinkResponseEntity.getBody()).getName())
        .isEqualTo(greenTea.getName());

    drinks = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/drinks", DrinkEntity[].class).clone());

    assertThat(drinks.size()).isEqualTo(5);
    assertThat(drinks.get(4).getName()).isEqualTo(greenTea.getName());
    assertThat(drinks.get(4).getAmount().doubleValue())
        .isEqualTo(greenTea.getAmount().doubleValue());

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<DrinkInput> request = new HttpEntity<>(
        new DrinkInput(updatedGreenTea.getName(), updatedGreenTea.getAmount()));
    restTemplate.getInterceptors().add(
        new BasicAuthenticationInterceptor("admin", "admin"));

    drinkResponseEntity = restTemplate
        .exchange(LOCAL_HOST + port + "/admin/products/drinks/5", HttpMethod.PUT, request,
            Drink.class);

    assertThat(Objects.requireNonNull(drinkResponseEntity.getBody()).getName())
        .isEqualTo(updatedGreenTea.getName());
    assertThat(Objects.requireNonNull(drinkResponseEntity.getBody()).getAmount().doubleValue())
        .isEqualTo(updatedGreenTea.getAmount().doubleValue());

    drinks = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/drinks", DrinkEntity[].class).clone());

    assertThat(drinks.size()).isEqualTo(5);
    assertThat(drinks.get(4).getName()).isEqualTo(updatedGreenTea.getName());
    assertThat(drinks.get(4).getAmount().doubleValue())
        .isEqualTo(updatedGreenTea.getAmount().doubleValue());

    testRestTemplate.withBasicAuth("admin", "admin")
        .delete(LOCAL_HOST + port + "/admin/products/drinks/5");

    drinks = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/drinks", DrinkEntity[].class).clone());

    assertThat(drinks.size()).isEqualTo(4);
    assertThat(drinks.stream().map(DrinkEntity::getId).collect(Collectors.toList())).doesNotContain(5L);
  }

  @Test
  public void testCrudToppings() {
    Topping lime = Topping.createWithIdAndNameAndPrice(5L, "Lime", BigDecimal.valueOf(3.0));
    Topping updatedLime = Topping.createWithIdAndNameAndPrice(5L, "Lime", BigDecimal.valueOf(4.0));

    List<ToppingEntity> toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());

    assertThat(toppings.size()).isEqualTo(4);

    ResponseEntity<Topping> drinkResponseEntity = testRestTemplate.withBasicAuth("admin", "admin")
        .postForEntity(LOCAL_HOST + port + "/admin/products/toppings",
            new ToppingInput(lime.getName(), lime.getAmount()), Topping.class);

    assertThat(Objects.requireNonNull(drinkResponseEntity.getBody()).getName())
        .isEqualTo(lime.getName());

    toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());

    assertThat(toppings.size()).isEqualTo(5);
    assertThat(toppings.get(4).getName()).isEqualTo(lime.getName());
    assertThat(toppings.get(4).getAmount().doubleValue())
        .isEqualTo(lime.getAmount().doubleValue());

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<ToppingInput> request = new HttpEntity<>(
        new ToppingInput(updatedLime.getName(), updatedLime.getAmount()));
    restTemplate.getInterceptors().add(
        new BasicAuthenticationInterceptor("admin", "admin"));

    drinkResponseEntity = restTemplate
        .exchange(LOCAL_HOST + port + "/admin/products/toppings/5", HttpMethod.PUT, request,
            Topping.class);

    assertThat(Objects.requireNonNull(drinkResponseEntity.getBody()).getName())
        .isEqualTo(updatedLime.getName());
    assertThat(Objects.requireNonNull(drinkResponseEntity.getBody()).getAmount().doubleValue())
        .isEqualTo(updatedLime.getAmount().doubleValue());

    toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());

    assertThat(toppings.size()).isEqualTo(5);
    assertThat(toppings.get(4).getName()).isEqualTo(updatedLime.getName());
    assertThat(toppings.get(4).getAmount().doubleValue())
        .isEqualTo(updatedLime.getAmount().doubleValue());

    testRestTemplate.withBasicAuth("admin", "admin")
        .delete(LOCAL_HOST + port + "/admin/products/toppings/5");

    toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());

    assertThat(toppings.size()).isEqualTo(4);
    assertThat(toppings.stream().map(ToppingEntity::getId).collect(Collectors.toList())).doesNotContain(5L);
  }
}
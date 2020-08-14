package tr.com.yigithanbalci.shoppingcartservice.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

  // TODO: 14.08.2020 Basic Authentication does not work. Returns 401.
  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  public void testCrudToppings(){
//    ToppingEntity mapleSyrup = ToppingEntity.builder().id(5L).name("MapleSyrup").price(4.0f).build();
//
//    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//    String adminpass = bCryptPasswordEncoder.encode("admin");
//    RestTemplate restTemplate = new RestTemplate();
//    restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));
//    restTemplate.postForEntity(LOCAL_HOST + port + "/admin/products/toppings", mapleSyrup, ToppingEntity.class);
//    ResponseEntity<ToppingEntity> toppings = testRestTemplate.withBasicAuth("admin", "admin")
//        .postForEntity(LOCAL_HOST + port + "/admin/products/toppings", mapleSyrup, ToppingEntity.class);
//
//    toppings.equals(mapleSyrup);
//    List<ToppingEntity> toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
//        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());
//
//    List<ToppingEntity> toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
//        .put(LOCAL_HOST + port + "/admin/products/drinks", ToppingEntity[].class).clone());
//
//    List<ToppingEntity> toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
//        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());
//
//    List<ToppingEntity> toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
//        .delete(LOCAL_HOST + port + "/admin/products/drinks", ToppingEntity[].class).clone());
//
//    List<ToppingEntity> toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
//        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());

//    assertThat(toppings.size()).isEqualTo(4);
//    assertThat(toppings.get(0).getId()).isEqualTo(milk.getId());
//    assertThat(toppings.get(1).getName()).isEqualTo(hazelnutSyrup.getName());
//    assertThat(toppings.get(2).getPrice()).isEqualTo(chocolateSauce.getPrice());
//    assertThat(toppings.get(3).getPrice()).isEqualTo(lemon.getPrice());
  }
}
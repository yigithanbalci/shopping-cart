//package tr.com.yigithanbalci.shoppingcartservice.integration;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.Arrays;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.test.annotation.IfProfileValue;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
//import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("dev")
//@IfProfileValue(name = "spring.profiles.active", value = "dev")
//public class ReportsRestControllerIntegrationTests {
//
//  @Autowired
//  private TestRestTemplate testRestTemplate;
//
//  @LocalServerPort
//  private int port;
//
//  private static final String LOCAL_HOST = "http://localhost:";
//
//  @Test
//  public void testCustomerAnalysis(){
//    List<CustomerAnalysis> customerAnalyses = Arrays.asList(testRestTemplate.withBasicAuth("admin", "admin")
//        .getForObject(LOCAL_HOST + port + "/admin/reports/users/total-orders", CustomerAnalysis[].class).clone());
//
//    assertThat(customerAnalyses.size()).isEqualTo(3);
////    assertThat(toppings.get(0).getId()).isEqualTo(milk.getId());
////    assertThat(toppings.get(1).getName()).isEqualTo(hazelnutSyrup.getName());
////    assertThat(toppings.get(2).getPrice()).isEqualTo(chocolateSauce.getPrice());
////    assertThat(toppings.get(3).getPrice()).isEqualTo(lemon.getPrice());
//  }
//
//  @Test
//  public void testDrinkAnalysis(){
//    ToppingEntity milk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(2.0);
//    ToppingEntity hazelnutSyrup = Topping.createWithIdAndNameAndPrice(2L, "Hazelnut syrup", 3.0f);
//    ToppingEntity chocolateSauce = Topping.createWithIdAndNameAndPrice(3L, "Chocolate sauce", BigDecimal.valueOf(5.0));
//    ToppingEntity lemon = Topping.createWithIdAndNameAndPrice(4L, "Lemon", BigDecimal.valueOf(2.0));
//
//    List<ToppingEntity> toppings = Arrays.asList(testRestTemplate.withBasicAuth("user1", "user1")
//        .getForObject(LOCAL_HOST + port + "/products/toppings", ToppingEntity[].class).clone());
//
//    assertThat(toppings.size()).isEqualTo(4);
//    assertThat(toppings.get(0).getId()).isEqualTo(milk.getId());
//    assertThat(toppings.get(1).getName()).isEqualTo(hazelnutSyrup.getName());
//    assertThat(toppings.get(2).getPrice()).isEqualTo(chocolateSauce.getPrice());
//    assertThat(toppings.get(3).getPrice()).isEqualTo(lemon.getPrice());
//  }
//}
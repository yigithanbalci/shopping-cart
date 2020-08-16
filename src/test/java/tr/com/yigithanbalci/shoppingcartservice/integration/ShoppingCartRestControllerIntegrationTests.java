//package tr.com.yigithanbalci.shoppingcartservice.integration;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.annotation.IfProfileValue;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
//import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
//import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
//import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("dev")
//@IfProfileValue(name = "spring.profiles.active", value = "dev")
//@DirtiesContext
//public class ShoppingCartRestControllerIntegrationTests {
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
//  public void testShoppingCart(){
//    Topping milk = Topping.builder().id(1L, "Milk", BigDecimal.valueOf(2.0));
//    Topping hazelnutSyrup = Topping.builder().id(2L, "Hazelnut syrup", BigDecimal.valueOf(2.0));
//    Topping lemon = Topping.builder().id(3L, "Lemon", BigDecimal.valueOf(2.0));
//
//    Drink blackCoffee = Drink.builder().id(1L, "Black Coffee", 4.0f);
//    Drink latte = Drink.builder().id(2L, "Latte", 4.0f);
//    Drink tea = Drink.builder().id(3L, "Tea", 3.0f);
//
//    Item blackCoffeeItem = new Item(blackCoffee);
//    blackCoffeeItem.addTopping(hazelnutSyrup);
//
//    Item latteItem = new Item(latte);
//    latteItem.addTopping(milk);
//
//    Item teaItem = new Item(tea);
//    teaItem.addTopping(lemon);
//
//    Cart cart = testRestTemplate.withBasicAuth("user1", "user1")
//        .postForObject(LOCAL_HOST + port + "/users/1/cart", blackCoffeeItem, Cart.class);
//
//    assertThat(cart.getItems().size()).isEqualTo(1);
//    assertThat(cart.getItems().get(0).getDrink().getId()).isEqualTo(blackCoffee.getId());
//
//    cart = testRestTemplate.withBasicAuth("user1", "user1")
//        .postForObject(LOCAL_HOST + port + "/users/1/cart", latteItem, Cart.class);
//
//    assertThat(cart.getItems().size()).isEqualTo(2);
//    assertThat(cart.getItems().get(1).getDrink().getId()).isEqualTo(latte.getId());
//
//    cart = testRestTemplate.withBasicAuth("user1", "user1")
//        .postForObject(LOCAL_HOST + port + "/users/1/cart", teaItem, Cart.class);
//
//    assertThat(cart.getItems().size()).isEqualTo(3);
//    assertThat(cart.getItems().get(2).getDrink().getId()).isEqualTo(tea.getId());
//
//    testRestTemplate.withBasicAuth("user1", "user1")
//        .put(LOCAL_HOST + port + "/users/1/cart", teaItem, Cart.class);
//
//    testRestTemplate.withBasicAuth("user1", "user1")
//        .put(LOCAL_HOST + port + "/users/1/cart", latteItem, Cart.class);
//
//    cart = testRestTemplate.withBasicAuth("user1", "user1")
//        .postForObject(LOCAL_HOST + port + "/users/1/cart", latteItem, Cart.class);
//
//    assertThat(cart.getItems().size()).isEqualTo(2);
//    assertThat(cart.getItems().get(1).getDrink().getId()).isEqualTo(latte.getId());
//
////    testRestTemplate.withBasicAuth("user1", "user1")
////        .put(LOCAL_HOST + port + "/users/1/cart/checkout", FinalizedCart.class);
//  }
//}
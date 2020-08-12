package tr.com.yigithanbalci.shoppingcartservice.unit.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tr.com.yigithanbalci.shoppingcartservice.auth.UserDetailsImpl;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.exception.CustomerNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;
import tr.com.yigithanbalci.shoppingcartservice.web.ShoppingCartRestController;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartRestControllerTests {

  @Mock
  private ShoppingService shoppingService;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    ShoppingCartRestController shoppingCartRestController = new ShoppingCartRestController(
        shoppingService);
    mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartRestController).build();
  }

  @Test
  public void testAddToCart() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito.mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getUserId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    Item americano = new Item(Drink.builder().name("Americano").price(4.0f).build());
    Topping milk = Topping.builder().name("Milk").price(3.0f).build();
    americano.addTopping(milk);

    Item latte = new Item(Drink.builder().name("Latte").price(5.0f).build());
    Topping caramelSyrup = Topping.builder().name("Caramel syrup").price(4.0f).build();
    latte.addTopping(caramelSyrup);

    Cart cart = new Cart();
    cart.addItem(americano);

    Cart itemAddedCart = new Cart();
    itemAddedCart.addItem(americano);
    itemAddedCart.addItem(latte);

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(shoppingService.addItemToCart(latte, 1L)).willReturn(itemAddedCart);

    String requestJson = ow.writeValueAsString(latte);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/users/1/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.amount", is((double)itemAddedCart.getAmount())))
        .andExpect(jsonPath("$.items[1].drink.name", is(latte.getDrink().getName())));
  }

  @Test
  public void testDeleteFromCart() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito.mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getUserId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    Item americano = new Item(Drink.builder().name("Americano").price(4.0f).build());
    Topping milk = Topping.builder().name("Milk").price(3.0f).build();
    americano.addTopping(milk);

    Item latte = new Item(Drink.builder().name("Latte").price(5.0f).build());
    Topping caramelSyrup = Topping.builder().name("Caramel syrup").price(4.0f).build();
    latte.addTopping(caramelSyrup);

    Cart cart = new Cart();
    cart.addItem(americano);

    Cart itemAddedCart = new Cart();
    itemAddedCart.addItem(americano);
    itemAddedCart.addItem(latte);

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(shoppingService.deleteItemFromCart(latte, 1L)).willReturn(cart);

    String requestJson = ow.writeValueAsString(latte);

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/users/1/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.amount", is((double)cart.getAmount())))
        .andExpect(jsonPath("$.items[0].drink.name", is(americano.getDrink().getName())));
  }

  @Test
  public void testCheckoutCart() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito.mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getUserId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    Item americano = new Item(Drink.builder().name("Americano").price(4.0f).build());
    Topping milk = Topping.builder().name("Milk").price(3.0f).build();
    americano.addTopping(milk);

    Item latte = new Item(Drink.builder().name("Latte").price(5.0f).build());
    Topping caramelSyrup = Topping.builder().name("Caramel syrup").price(4.0f).build();
    latte.addTopping(caramelSyrup);

    Cart cart = new Cart();
    cart.addItem(americano);
    cart.addItem(latte);

    FinalizedCart finalizedCart = FinalizedCart.builder().originalAmount(16.0f).discountedAmount(16.0f * 0.75f).build();

    given(shoppingService.checkoutCart(1L)).willReturn(finalizedCart);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/users/1/cart/checkout")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.originalAmount", is((double)finalizedCart.getOriginalAmount())))
        .andExpect(jsonPath("$.discountedAmount", is((double)finalizedCart.getDiscountedAmount())));
  }

  @Test
  public void whenException_thenInternalServerError() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito.mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getUserId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    Item latte = new Item(Drink.builder().name("Latte").price(5.0f).build());

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(shoppingService.addItemToCart(latte, 1L)).willThrow(new RuntimeException("test"));
    given(shoppingService.deleteItemFromCart(latte, 1L)).willThrow(new RuntimeException("test"));
    given(shoppingService.checkoutCart(1L)).willThrow(new RuntimeException("test"));

    String requestJson = ow.writeValueAsString(latte);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/users/1/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isInternalServerError());

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/users/1/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isInternalServerError());

    mockMvc.perform(
        MockMvcRequestBuilders.put("/users/1/cart/checkout")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void whenUnAuthorized_thenUnAuthorized() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito.mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getUserId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    Item latte = new Item(Drink.builder().name("Latte").price(5.0f).build());

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    String requestJson = ow.writeValueAsString(latte);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/users/2/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isUnauthorized());

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/users/2/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isUnauthorized());

    mockMvc.perform(
        MockMvcRequestBuilders.put("/users/2/cart/checkout")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void whenCustomerNotFound_thenNotFound() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito.mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getUserId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    given(shoppingService.checkoutCart(1L)).willThrow(new CustomerNotFoundException("test"));

    mockMvc.perform(
        MockMvcRequestBuilders.put("/users/1/cart/checkout")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal))
        .andExpect(status().isNotFound());
  }
}
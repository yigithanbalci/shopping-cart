package tr.com.yigithanbalci.shoppingcartservice.unit.web;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.security.UserDetailsImpl;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;
import tr.com.yigithanbalci.shoppingcartservice.web.ShoppingCartRestController;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartRestControllerTests {

  @Mock
  private ShoppingService shoppingService;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    ShoppingCartRestController shoppingCartRestController = new ShoppingCartRestController(
        shoppingService);
    mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartRestController).build();
  }

  @Test
  public void testAddToCart() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito
        .mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getCustomerId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    Item americano = Item.createWithDrink(
        Drink.createWithIdAndNameAndPrice(1L, "Americano", BigDecimal.valueOf(4.0)));
    Topping milk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(3.0));
    americano.addTopping(milk);

    Item latte = Item
        .createWithDrink(Drink.createWithIdAndNameAndPrice(2L, "Latte", BigDecimal.valueOf(5.0)));
    Topping caramelSyrup = Topping
        .createWithIdAndNameAndPrice(2L, "Caramel syrup", BigDecimal.valueOf(4.0));
    latte.addTopping(caramelSyrup);

    ItemInput latteInput = new ItemInput(latte.getDrink().getId(),
        latte.getToppings().stream().map(Topping::getId).collect(
            Collectors.toList()));

    Cart cart = new Cart();
    cart.addItem(americano);

    Cart itemAddedCart = new Cart();
    itemAddedCart.addItem(americano);
    itemAddedCart.addItem(latte);

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(shoppingService.addItemToCart(latteInput, 1L)).willReturn(itemAddedCart);

    String requestJson = ow.writeValueAsString(latteInput);

    mockMvc.perform(
        MockMvcRequestBuilders.post("/users/1/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.amount", is(itemAddedCart.getAmount().doubleValue())))
        .andExpect(jsonPath("$.items[1].drink.name", is(latte.getDrink().getName())));
  }

  @Test
  public void testDeleteFromCart() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito
        .mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getCustomerId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    Item americano = Item.createWithDrink(
        Drink.createWithIdAndNameAndPrice(1L, "Americano", BigDecimal.valueOf(4.0)));
    Topping milk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(3.0));
    americano.addTopping(milk);

    Item latte = Item
        .createWithDrink(Drink.createWithIdAndNameAndPrice(2L, "Latte", BigDecimal.valueOf(5.0)));
    Topping caramelSyrup = Topping
        .createWithIdAndNameAndPrice(2L, "Caramel syrup", BigDecimal.valueOf(4.0));
    latte.addTopping(caramelSyrup);

    ItemInput latteInput = new ItemInput(latte.getDrink().getId(),
        latte.getToppings().stream().map(Topping::getId).collect(
            Collectors.toList()));

    Cart cart = new Cart();
    cart.addItem(americano);

    Cart itemAddedCart = new Cart();
    itemAddedCart.addItem(americano);
    itemAddedCart.addItem(latte);

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(shoppingService.deleteItemFromCart(latteInput, 1L)).willReturn(cart);

    String requestJson = ow.writeValueAsString(latteInput);

    mockMvc.perform(
        MockMvcRequestBuilders.put("/users/1/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.amount", is(cart.getAmount().doubleValue())))
        .andExpect(jsonPath("$.items[0].drink.name", is(americano.getDrink().getName())));
  }

  @Test
  public void testCheckoutCart() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito
        .mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getCustomerId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    Item americano = Item
        .createWithDrink(
            Drink.createWithIdAndNameAndPrice(1L, "Americano", BigDecimal.valueOf(4.0)));
    Topping milk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(3.0));
    americano.addTopping(milk);

    Item latte = Item
        .createWithDrink(Drink.createWithIdAndNameAndPrice(2L, "Latte", BigDecimal.valueOf(5.0)));
    Topping caramelSyrup = Topping
        .createWithIdAndNameAndPrice(2L, "Caramel syrup", BigDecimal.valueOf(4.0));
    latte.addTopping(caramelSyrup);

    Cart cart = new Cart();
    cart.addItem(americano);
    cart.addItem(latte);

    FinalizedCart finalizedCart = FinalizedCart
        .createWithOriginalAmountAndDiscountedAmount(BigDecimal.valueOf(16.0),
            BigDecimal.valueOf(16.0).multiply(BigDecimal.valueOf(0.75)));

    given(shoppingService.checkoutCart(1L)).willReturn(finalizedCart);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/users/1/cart/checkout")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.originalAmount", is(finalizedCart.getOriginalAmount().doubleValue())))
        .andExpect(
            jsonPath("$.discountedAmount", is(finalizedCart.getDiscountedAmount().doubleValue())));
  }

  @Test
  public void whenUnAuthorized_thenUnAuthorized() throws Exception {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito
        .mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getCustomerId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    Item latte = Item
        .createWithDrink(Drink.createWithIdAndNameAndPrice(1L, "Latte", BigDecimal.valueOf(5.0)));

    ItemInput latteInput = new ItemInput(latte.getDrink().getId(),
        latte.getToppings().stream().map(Topping::getId).collect(
            Collectors.toList()));

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    String requestJson = ow.writeValueAsString(latteInput);

    mockMvc.perform(
        MockMvcRequestBuilders.post("/users/2/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isUnauthorized());

    mockMvc.perform(
        MockMvcRequestBuilders.put("/users/2/cart")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal).content(requestJson))
        .andExpect(status().isUnauthorized());

    mockMvc.perform(
        MockMvcRequestBuilders.get("/users/2/cart/checkout")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void whenCustomerNotFound_thenNotFound() {
    UsernamePasswordAuthenticationToken mockPrincipal = Mockito
        .mock(UsernamePasswordAuthenticationToken.class);
    UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
    Mockito.when(userDetails.getCustomerId()).thenReturn(1L);
    Mockito.when(mockPrincipal.getPrincipal()).thenReturn(userDetails);

    given(shoppingService.checkoutCart(1L)).willThrow(new EntityNotFoundException("test"));

    assertThatThrownBy(() -> mockMvc.perform(
        MockMvcRequestBuilders.get("/users/1/cart/checkout")
            .contentType(MediaType.APPLICATION_JSON).principal(mockPrincipal)))
        .hasCause(new EntityNotFoundException("test"));
  }
}
package tr.com.yigithanbalci.shoppingcartservice.unit.web;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;
import tr.com.yigithanbalci.shoppingcartservice.web.ProductsCustomerRestController;

@ExtendWith(MockitoExtension.class)
public class ProductsCustomerRestControllerTests {

  @Mock
  private DrinkService drinkService;

  @Mock
  private ToppingService toppingService;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    ProductsCustomerRestController productsCustomerRestController = new ProductsCustomerRestController(
        drinkService, toppingService);
    mockMvc = MockMvcBuilders.standaloneSetup(productsCustomerRestController).build();
  }

  @Test
  public void testFindDrinks() throws Exception {
    Drink blackCoffee = Drink.createWithIdAndNameAndPrice(1L, "Black Coffee", BigDecimal.valueOf(4.0));
    Drink latte =Drink.createWithIdAndNameAndPrice(2L, "Latte", BigDecimal.valueOf(5.0));
    Drink mocha = Drink.createWithIdAndNameAndPrice(3L, "Mocha", BigDecimal.valueOf(6.0));
    Drink tea = Drink.createWithIdAndNameAndPrice(4L, "Tea", BigDecimal.valueOf(3.0));

    List<Drink> drinks = new ArrayList<>();
    drinks.add(blackCoffee);
    drinks.add(latte);
    drinks.add(mocha);
    drinks.add(tea);

    given(drinkService.findAll()).willReturn(drinks);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/products/drinks").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].name", is(blackCoffee.getName())));
  }

  @Test
  public void testFindToppings() throws Exception {
    Topping milk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(2.0));
    Topping hazelnutSyrup = Topping.createWithIdAndNameAndPrice(2L, "Hazelnut syrup", BigDecimal.valueOf(3.0));
    Topping chocolateSauce = Topping.createWithIdAndNameAndPrice(3L, "Chocolate sauce", BigDecimal.valueOf(5.0));
    Topping lemon = Topping.createWithIdAndNameAndPrice(4L, "Lemon", BigDecimal.valueOf(2.0));

    List<Topping> toppings = new ArrayList<>();
    toppings.add(milk);
    toppings.add(hazelnutSyrup);
    toppings.add(chocolateSauce);
    toppings.add(lemon);

    given(toppingService.findAll()).willReturn(toppings);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/products/toppings").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[1].name", is(hazelnutSyrup.getName())));
  }

  @Test
  public void whenNotFound_thenNotFound() {
    given(drinkService.findAll()).willThrow(new EntityNotFoundException("test"));
    given(toppingService.findAll()).willThrow(new EntityNotFoundException("test"));

    assertThatThrownBy(() -> mockMvc.perform(
        MockMvcRequestBuilders.get("/products/drinks").contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new EntityNotFoundException("test"));

    assertThatThrownBy(() -> mockMvc.perform(
        MockMvcRequestBuilders.get("/products/toppings").contentType(MediaType.APPLICATION_JSON)))
        .hasCause(new EntityNotFoundException("test"));
  }
}
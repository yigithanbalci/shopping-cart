package tr.com.yigithanbalci.shoppingcartservice.unit.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tr.com.yigithanbalci.shoppingcartservice.exception.DrinkNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.exception.ToppingNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;
import tr.com.yigithanbalci.shoppingcartservice.web.ProductsCustomerRestController;

@RunWith(MockitoJUnitRunner.class)
public class ProductsCustomerRestControllerTests {

  @Mock
  private DrinkService drinkService;

  @Mock
  private ToppingService toppingService;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    ProductsCustomerRestController productsCustomerRestController = new ProductsCustomerRestController(
        drinkService, toppingService);
    mockMvc = MockMvcBuilders.standaloneSetup(productsCustomerRestController).build();
  }

  @Test
  public void testFindDrinks() throws Exception {
    DrinkEntity blackCoffee = DrinkEntity.builder().id(1L).name("Black Coffee").price(4.0f).build();
    DrinkEntity latte = DrinkEntity.builder().id(2L).name("Latte").price(5.0f).build();
    DrinkEntity mocha = DrinkEntity.builder().id(3L).name("Mocha").price(6.0f).build();
    DrinkEntity tea = DrinkEntity.builder().id(4L).name("Tea").price(3.0f).build();

    List<DrinkEntity> drinks = new ArrayList<>();
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
    ToppingEntity milk = ToppingEntity.builder().id(1L).name("Milk").price(2.0f).build();
    ToppingEntity hazelnutSyrup = ToppingEntity.builder().id(2L).name("Hazelnut syrup").price(3.0f)
        .build();
    ToppingEntity chocolateSauce = ToppingEntity.builder().id(3L).name("Chocolate sauce")
        .price(5.0f)
        .build();
    ToppingEntity lemon = ToppingEntity.builder().id(4L).name("Lemon").price(2.0f).build();

    List<ToppingEntity> toppings = new ArrayList<>();
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
  public void whenException_thenInternalServerError() throws Exception {
    given(drinkService.findAll()).willThrow(new RuntimeException("test"));
    given(toppingService.findAll()).willThrow(new RuntimeException("test"));

    mockMvc.perform(
        MockMvcRequestBuilders.get("/products/drinks").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());

    mockMvc.perform(
        MockMvcRequestBuilders.get("/products/toppings").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void whenNotFound_thenNotFound() throws Exception {
    given(drinkService.findAll()).willThrow(new DrinkNotFoundException("test"));
    given(toppingService.findAll()).willThrow(new ToppingNotFoundException("test"));

    mockMvc.perform(
        MockMvcRequestBuilders.get("/products/drinks").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    mockMvc.perform(
        MockMvcRequestBuilders.get("/products/toppings").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
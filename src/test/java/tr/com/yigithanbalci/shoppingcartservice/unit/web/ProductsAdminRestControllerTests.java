package tr.com.yigithanbalci.shoppingcartservice.unit.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.exception.DrinkNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.exception.ToppingNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;
import tr.com.yigithanbalci.shoppingcartservice.web.ProductsAdminRestController;

@RunWith(MockitoJUnitRunner.class)
public class ProductsAdminRestControllerTests {

  @Mock
  private DrinkService drinkService;

  @Mock
  private ToppingService toppingService;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    ProductsAdminRestController productsAdminRestController = new ProductsAdminRestController(
        drinkService, toppingService);
    mockMvc = MockMvcBuilders.standaloneSetup(productsAdminRestController).build();
  }

  @Test
  public void testCRUDDrinks() throws Exception {
    Drink blackCoffee = Drink.builder().name("Black Coffee").price(4.0f).build();
    DrinkEntity blackCoffeeEntity = DrinkEntity.builder().id(1L).name("Black Coffee").price(4.0f)
        .build();
    Drink updatedBlackCoffee = Drink.builder().name("Black Coffee").price(5.0f).build();
    DrinkEntity updatedBlackCoffeeEntity = DrinkEntity.builder().id(1L).name("Black Coffee")
        .price(5.0f)
        .build();

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(drinkService.createDrink(DrinkEntity.from(blackCoffee))).willReturn(blackCoffeeEntity);
    given(drinkService.updateDrink(updatedBlackCoffeeEntity)).willReturn(updatedBlackCoffeeEntity);

    String requestJson = ow.writeValueAsString(blackCoffee);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/admin/products/drinks")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(blackCoffee.getName())));
    Mockito.verify(drinkService, Mockito.times(1)).createDrink(DrinkEntity.from(blackCoffee));

    requestJson = ow.writeValueAsString(updatedBlackCoffee);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/drinks/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price", is(5.0)));
    Mockito.verify(drinkService, Mockito.times(1)).updateDrink(updatedBlackCoffeeEntity);

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/admin/products/drinks/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());
    Mockito.verify(drinkService, Mockito.times(1)).deleteDrink(updatedBlackCoffeeEntity.getId());
  }

  @Test
  public void testCRUDToppings() throws Exception {
    Topping milk = Topping.builder().name("Milk").price(2.0f).build();
    ToppingEntity milkEntity = ToppingEntity.builder().id(1L).name("Milk").price(2.0f).build();
    Topping updatedMilk = Topping.builder().name("Milk").price(3.0f).build();
    ToppingEntity updatedHazelnutSyrupEntity = ToppingEntity.builder().id(1L).name("Milk")
        .price(3.0f).build();

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(toppingService.createTopping(ToppingEntity.from(milk))).willReturn(milkEntity);
    given(toppingService.updateTopping(updatedHazelnutSyrupEntity))
        .willReturn(updatedHazelnutSyrupEntity);

    String requestJson = ow.writeValueAsString(milk);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/admin/products/toppings")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(milk.getName())));
    Mockito.verify(toppingService, Mockito.times(1)).createTopping(ToppingEntity.from(milk));

    requestJson = ow.writeValueAsString(updatedMilk);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/toppings/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price", is(3.0)));
    Mockito.verify(toppingService, Mockito.times(1)).updateTopping(updatedHazelnutSyrupEntity);

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/admin/products/toppings/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());
    Mockito.verify(toppingService, Mockito.times(1))
        .deleteTopping(updatedHazelnutSyrupEntity.getId());
  }

  @Test
  public void whenExceptionInDrinks_thenInternalServerError() throws Exception {
    Drink blackCoffee = Drink.builder().name("Black Coffee").price(4.0f).build();
    Drink updatedBlackCoffee = Drink.builder().name("Black Coffee").price(5.0f).build();
    DrinkEntity updatedBlackCoffeeEntity = DrinkEntity.builder().id(1L).name("Black Coffee")
        .price(5.0f)
        .build();

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(drinkService.createDrink(DrinkEntity.from(blackCoffee))).willThrow(new RuntimeException("test"));
    given(drinkService.updateDrink(updatedBlackCoffeeEntity)).willThrow(new RuntimeException("test"));
    doThrow(new RuntimeException("test")).when(drinkService).deleteDrink(1L);

    String requestJson = ow.writeValueAsString(blackCoffee);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/admin/products/drinks")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isInternalServerError());

    requestJson = ow.writeValueAsString(updatedBlackCoffee);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/drinks/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isInternalServerError());

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/admin/products/drinks/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void whenExceptionInToppings_thenInternalServerError() throws Exception {
    Topping milk = Topping.builder().name("Milk").price(2.0f).build();
    Topping updatedMilk = Topping.builder().name("Milk").price(3.0f).build();
    ToppingEntity updatedHazelnutSyrupEntity = ToppingEntity.builder().id(1L).name("Milk")
        .price(3.0f).build();

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(toppingService.createTopping(ToppingEntity.from(milk))).willThrow(new RuntimeException("test"));
    given(toppingService.updateTopping(updatedHazelnutSyrupEntity)).willThrow(new RuntimeException("test"));
    doThrow(new RuntimeException("test")).when(toppingService).deleteTopping(1L);

    String requestJson = ow.writeValueAsString(milk);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/admin/products/toppings")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isInternalServerError());

    requestJson = ow.writeValueAsString(updatedMilk);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/toppings/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isInternalServerError());

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/admin/products/toppings/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void whenDrinkNotFound_thenNotFound() throws Exception {
    Drink updatedBlackCoffee = Drink.builder().name("Black Coffee").price(5.0f).build();
    DrinkEntity updatedBlackCoffeeEntity = DrinkEntity.builder().id(1L).name("Black Coffee")
        .price(5.0f)
        .build();

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(drinkService.updateDrink(updatedBlackCoffeeEntity)).willThrow(new DrinkNotFoundException("test"));

    String requestJson = ow.writeValueAsString(updatedBlackCoffee);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/drinks/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isNotFound());
  }

  @Test
  public void whenToppingNotFound_thenNotFound() throws Exception {
    Topping updatedMilk = Topping.builder().name("Milk").price(3.0f).build();
    ToppingEntity updatedHazelnutSyrupEntity = ToppingEntity.builder().id(1L).name("Milk")
        .price(3.0f).build();

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(toppingService.updateTopping(updatedHazelnutSyrupEntity)).willThrow(new ToppingNotFoundException("test"));

    String requestJson = ow.writeValueAsString(updatedMilk);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/toppings/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isNotFound());
  }
}

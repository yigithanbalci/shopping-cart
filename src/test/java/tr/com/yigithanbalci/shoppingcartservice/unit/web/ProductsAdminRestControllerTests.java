package tr.com.yigithanbalci.shoppingcartservice.unit.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.DrinkInput;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.dto.ToppingInput;
import tr.com.yigithanbalci.shoppingcartservice.exception.DrinkNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.exception.ToppingNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;
import tr.com.yigithanbalci.shoppingcartservice.web.ProductsAdminRestController;

@ExtendWith(MockitoExtension.class)
public class ProductsAdminRestControllerTests {

  @Mock
  private DrinkService drinkService;

  @Mock
  private ToppingService toppingService;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    ProductsAdminRestController productsAdminRestController = new ProductsAdminRestController(
        drinkService, toppingService);
    mockMvc = MockMvcBuilders.standaloneSetup(productsAdminRestController).build();
  }

  @Test
  public void testCRUDDrinks() throws Exception {
    Drink blackCoffee = Drink
        .createWithIdAndNameAndPrice(1L, "Black Coffee", BigDecimal.valueOf(4.0));
    DrinkInput blackCoffeeToCreate = new DrinkInput("Black Coffee", BigDecimal.valueOf(4.0));
    Drink updatedBlackCoffee = Drink
        .createWithIdAndNameAndPrice(1L, "Black Coffee", BigDecimal.valueOf(5.0));
    DrinkInput updatedBlackCoffeeInput = new DrinkInput("Black Coffee", BigDecimal.valueOf(5.0));

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(drinkService.create(blackCoffeeToCreate)).willReturn(blackCoffee);
    given(drinkService.update(updatedBlackCoffee)).willReturn(updatedBlackCoffee);

    String requestJson = ow.writeValueAsString(blackCoffeeToCreate);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/admin/products/drinks")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(blackCoffee.getName())));
    Mockito.verify(drinkService, Mockito.times(1)).create(blackCoffeeToCreate);

    requestJson = ow.writeValueAsString(updatedBlackCoffeeInput);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/drinks/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.amount", is(5.0)));
    Mockito.verify(drinkService, Mockito.times(1)).update(updatedBlackCoffee);

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/admin/products/drinks/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());
    Mockito.verify(drinkService, Mockito.times(1)).delete(updatedBlackCoffee.getId());
  }

  @Test
  public void testCRUDToppings() throws Exception {
    Topping milk = Topping
        .createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(2.0));
    ToppingInput milkToCreate = new ToppingInput(milk.getName(), milk.getAmount());
    Topping updatedMilk = Topping
        .createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(3.0));
    ToppingInput updatedMilkInput = new ToppingInput(updatedMilk.getName(),
        updatedMilk.getAmount());

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(toppingService.create(milkToCreate)).willReturn(milk);
    given(toppingService.update(updatedMilk)).willReturn(updatedMilk);

    String requestJson = ow.writeValueAsString(milkToCreate);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/admin/products/toppings")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(milk.getName())));
    Mockito.verify(toppingService, Mockito.times(1)).create(milkToCreate);

    requestJson = ow.writeValueAsString(updatedMilkInput);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/toppings/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.amount", is(3.0)));
    Mockito.verify(toppingService, Mockito.times(1)).update(updatedMilk);

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/admin/products/toppings/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());
    Mockito.verify(toppingService, Mockito.times(1))
        .delete(updatedMilk.getId());
  }

  @Test
  public void whenExceptionInDrinks_thenInternalServerError() throws Exception {
    Drink blackCoffee = Drink
        .createWithIdAndNameAndPrice(1L, "Black Coffee", BigDecimal.valueOf(4.0));
    DrinkInput blackCoffeeToCreate = new DrinkInput("Black Coffee", BigDecimal.valueOf(4.0));
    Drink updatedBlackCoffee = Drink
        .createWithIdAndNameAndPrice(1L, "Black Coffee", BigDecimal.valueOf(5.0));
    DrinkInput updatedBlackCoffeeInput = new DrinkInput("Black Coffee", BigDecimal.valueOf(5.0));

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(drinkService.create(new DrinkInput(blackCoffee.getName(), blackCoffee.getAmount())))
        .willThrow(new RuntimeException("test"));
    given(drinkService.update(updatedBlackCoffee)).willThrow(new RuntimeException("test"));
    doThrow(new RuntimeException("test")).when(drinkService).delete(1L);

    String requestJson = ow.writeValueAsString(blackCoffeeToCreate);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/admin/products/drinks")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isInternalServerError());

    requestJson = ow.writeValueAsString(updatedBlackCoffeeInput);
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
    Topping milk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(2.0));
    Topping updatedMilk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(3.0));

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(toppingService.create(new ToppingInput(milk.getName(), milk.getAmount())))
        .willThrow(new RuntimeException("test"));
    given(toppingService.update(updatedMilk))
        .willThrow(new RuntimeException("test"));
    doThrow(new RuntimeException("test")).when(toppingService).delete(1L);

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
    Drink updatedBlackCoffee = Drink
        .createWithIdAndNameAndPrice(1L, "Black Coffee", BigDecimal.valueOf(5.0));
    DrinkInput updatedBlackCoffeeInput = new DrinkInput("Black Coffee", BigDecimal.valueOf(5.0));

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(drinkService.update(updatedBlackCoffee))
        .willThrow(new DrinkNotFoundException("test"));

    String requestJson = ow.writeValueAsString(updatedBlackCoffeeInput);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/drinks/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isNotFound());
  }

  @Test
  public void whenToppingNotFound_thenNotFound() throws Exception {
    Topping updatedMilk = Topping.createWithIdAndNameAndPrice(1L, "Milk", BigDecimal.valueOf(3.0));

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    given(toppingService.update(updatedMilk))
        .willThrow(new ToppingNotFoundException("test"));

    String requestJson = ow.writeValueAsString(updatedMilk);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/admin/products/toppings/1")
            .contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isNotFound());
  }
}

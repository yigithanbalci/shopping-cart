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
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedTopping;
import tr.com.yigithanbalci.shoppingcartservice.service.ReportService;
import tr.com.yigithanbalci.shoppingcartservice.web.ReportsRestController;

@RunWith(MockitoJUnitRunner.class)
public class ReportsRestControllerTests {

  @Mock
  private ReportService service;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    ReportsRestController reportsRestController = new ReportsRestController(service);
    mockMvc = MockMvcBuilders.standaloneSetup(reportsRestController).build();
  }

  @Test
  public void testCustomerReport() throws Exception {
    List<CustomerAnalysis> customerAnalysis = new ArrayList<>();
    CustomerAnalysis yigit = CustomerAnalysis.builder()
        .username("yigit").totalAmountOfOrders(3L).build();
    CustomerAnalysis ali = CustomerAnalysis.builder()
        .username("ali").totalAmountOfOrders(4L).build();
    customerAnalysis.add(yigit);
    customerAnalysis.add(ali);

    given(service.customerAnalysisReport()).willReturn(customerAnalysis);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/admin/reports/users/total-orders")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].username", is(yigit.getUsername())))
    .andExpect(jsonPath("$[0].totalAmountOfOrders", is(3)));
  }

  @Test
  public void testDrinkReport() throws Exception {
    List<DrinkAndMostUsedTopping> drinkAndMostUsedToppings = new ArrayList<>();
    DrinkAndMostUsedTopping tea = DrinkAndMostUsedTopping.builder()
        .drink("Tea").mostUsedTopping("Lemon").build();
    DrinkAndMostUsedTopping latte = DrinkAndMostUsedTopping.builder()
        .drink("Latte").mostUsedTopping("Milk").build();
    drinkAndMostUsedToppings.add(tea);
    drinkAndMostUsedToppings.add(latte);

    given(service.drinkAnalysisReport()).willReturn(drinkAndMostUsedToppings);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/admin/reports/drinks/most-used-topping")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].drink", is(tea.getDrink())))
        .andExpect(jsonPath("$[0].mostUsedTopping", is(tea.getMostUsedTopping())));
  }

  @Test
  public void whenException_thenInternalServerError() throws Exception {
    given(service.customerAnalysisReport()).willThrow(new RuntimeException("test"));
    given(service.drinkAnalysisReport()).willThrow(new RuntimeException("test"));

    mockMvc.perform(
        MockMvcRequestBuilders.get("/admin/reports/users/total-orders")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());

    mockMvc.perform(
        MockMvcRequestBuilders.get("/admin/reports/drinks/most-used-topping")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }
}

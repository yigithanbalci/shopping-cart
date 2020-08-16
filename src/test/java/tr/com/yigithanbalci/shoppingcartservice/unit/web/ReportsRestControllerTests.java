package tr.com.yigithanbalci.shoppingcartservice.unit.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedTopping;
import tr.com.yigithanbalci.shoppingcartservice.service.ReportService;
import tr.com.yigithanbalci.shoppingcartservice.web.ReportsRestController;

@ExtendWith(MockitoExtension.class)
public class ReportsRestControllerTests {

  @Mock
  private ReportService service;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    ReportsRestController reportsRestController = new ReportsRestController(service);
    mockMvc = MockMvcBuilders.standaloneSetup(reportsRestController).build();
  }

  @Test
  public void testCustomerReport() throws Exception {
    List<CustomerAnalysis> customerAnalysis = new ArrayList<>();
    CustomerAnalysis yigit = CustomerAnalysis.createWithUsernameAndTotalOrders("yigit", BigDecimal.valueOf(15.0));
    CustomerAnalysis ali = CustomerAnalysis.createWithUsernameAndTotalOrders("ali", BigDecimal.valueOf(20.0));
    customerAnalysis.add(yigit);
    customerAnalysis.add(ali);

    given(service.getCustomerAnalysisReport()).willReturn(customerAnalysis);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/admin/reports/users/total-orders")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].username", is(yigit.getUsername())))
        .andExpect(jsonPath("$[0].totalAmountOfOrders", is(yigit.getTotalAmountOfOrders().doubleValue())));
  }

  @Test
  public void testDrinkReport() throws Exception {
    List<DrinkAndMostUsedTopping> drinkAndMostUsedToppings = new ArrayList<>();
    DrinkAndMostUsedTopping tea = DrinkAndMostUsedTopping.createWithDrinkAndMostUsedTopping("Tea", "Lemon");
    DrinkAndMostUsedTopping latte = DrinkAndMostUsedTopping.createWithDrinkAndMostUsedTopping("Latte", "Milk");
    drinkAndMostUsedToppings.add(tea);
    drinkAndMostUsedToppings.add(latte);

    given(service.getDrinkAnalysisReport()).willReturn(drinkAndMostUsedToppings);

    mockMvc.perform(
        MockMvcRequestBuilders.get("/admin/reports/drinks/most-used-topping")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].drink", is(tea.getDrink())))
        .andExpect(jsonPath("$[0].mostUsedTopping", is(tea.getMostUsedTopping())));
  }
}
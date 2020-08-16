package tr.com.yigithanbalci.shoppingcartservice.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedTopping;
import tr.com.yigithanbalci.shoppingcartservice.service.ReportService;

@Slf4j
@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportsRestController {

  private final ReportService service;

  @Operation(summary = "Get total orders per user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customers analysed.",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = CustomerAnalysis.class)))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @GetMapping("/users/total-orders")
  public ResponseEntity<List<CustomerAnalysis>> getTotalOrderByUser() {
    List<CustomerAnalysis> customerAnalysis = service.getCustomerAnalysisReport();
    return ResponseEntity.ok(customerAnalysis);
  }

  @Operation(summary = "Get most used toppings for drinks.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Drinks and toppings analysed.",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = DrinkAndMostUsedTopping.class)))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @GetMapping("/drinks/most-used-topping")
  public ResponseEntity<List<DrinkAndMostUsedTopping>> getMostUsedToppingsForDrinks() {
    List<DrinkAndMostUsedTopping> drinkAnalysis = service.getDrinkAnalysisReport();
    return ResponseEntity.ok(drinkAnalysis);
  }
}

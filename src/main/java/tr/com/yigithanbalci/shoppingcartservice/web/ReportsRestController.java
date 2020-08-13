package tr.com.yigithanbalci.shoppingcartservice.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedTopping;
import tr.com.yigithanbalci.shoppingcartservice.exception.InternalServerException;
import tr.com.yigithanbalci.shoppingcartservice.service.ReportService;

@Slf4j
@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportsRestController {

  private final ReportService service;

  @GetMapping("/users/total-orders")
  public ResponseEntity<List<CustomerAnalysis>> getTotalOrderByUser(){
    try {
      List<CustomerAnalysis> customerAnalysis = service.customerAnalysisReport();
      return ResponseEntity.ok(customerAnalysis);
    } catch (Exception e) {
      log.error("Exception occurs while reporting order by user {}", e.getLocalizedMessage(), e);
      throw new InternalServerException("Order by user could not be reported: " + e.getLocalizedMessage());
    }
  }

  @GetMapping("/drinks/most-used-topping")
  public ResponseEntity<List<DrinkAndMostUsedTopping>> getMostUsedToppingsForDrinks(){
    try {
      List<DrinkAndMostUsedTopping> drinkAnalysis = service.drinkAnalysisReport();
      return ResponseEntity.ok(drinkAnalysis);
    } catch (Exception e) {
      log.error("Exception occurs while reporting toppings by drink {}", e.getLocalizedMessage(), e);
      throw new InternalServerException("Toppings by drink could not be reported: " + e.getLocalizedMessage());
    }
  }
}

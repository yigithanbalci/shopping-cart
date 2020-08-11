package tr.com.yigithanbalci.shoppingcartservice.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.yigithanbalci.shoppingcartservice.service.ReportService;

@Slf4j
@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportsRestController {

  @NonNull private final ReportService service;

  @GetMapping("/users/{userId}/total-orders")
  public ResponseEntity getTotalOrderByUser(@PathVariable("userId") String userId){
    service.customerAnalysisReport();
    return ResponseEntity.ok().build();
  }

  @GetMapping("/drinks/{drinkId}/most-used-topping")
  public ResponseEntity getMostUsedToppingsForDrinks(@PathVariable("drinkId") String drinkId){
    return ResponseEntity.ok().build();
  }
}

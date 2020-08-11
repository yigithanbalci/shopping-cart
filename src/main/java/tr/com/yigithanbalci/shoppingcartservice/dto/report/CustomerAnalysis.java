package tr.com.yigithanbalci.shoppingcartservice.dto.report;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CustomerAnalysis {

  private List<CustomerAndOrderWrapper> customers = new ArrayList<>();

  public void addCustomerAndOrders(CustomerAndOrderWrapper customerAndOrderWrapper){
    customers.add(customerAndOrderWrapper);
  }
}

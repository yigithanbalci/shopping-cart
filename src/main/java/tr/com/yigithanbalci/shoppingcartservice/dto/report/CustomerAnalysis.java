package tr.com.yigithanbalci.shoppingcartservice.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tr.com.yigithanbalci.shoppingcartservice.dto.SelfValidating;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerAnalysis extends SelfValidating<CustomerAnalysis> implements Serializable {

  @NotBlank(message = "Username is not blank")
  private String username;

  @PositiveOrZero(message = "Total amount of User's orders is positive or zero")
  private BigDecimal totalAmountOfOrders;

  public static CustomerAnalysis createWithUsernameAndTotalOrders(String username, BigDecimal totalAmountOfOrders) {
    CustomerAnalysis customerAnalysis = new CustomerAnalysis(username, totalAmountOfOrders);
    customerAnalysis.validateSelf();
    return customerAnalysis;
  }
}

package tr.com.yigithanbalci.shoppingcartservice.dto.report;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO: 12.08.2020 @data
// TODO: 12.08.2020 gereksiz builderlari kaldir
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerAnalysis implements Serializable {

  private String username;
  private Long totalAmountOfOrders;
}

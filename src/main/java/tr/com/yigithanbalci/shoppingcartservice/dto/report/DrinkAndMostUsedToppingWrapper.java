package tr.com.yigithanbalci.shoppingcartservice.dto.report;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DrinkAndMostUsedToppingWrapper {

  private String drink;
  private String mostUsedTopping;
}

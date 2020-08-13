package tr.com.yigithanbalci.shoppingcartservice.dto.report;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DrinkAndMostUsedTopping implements Serializable {

  private String drink;
  private String mostUsedTopping;
}

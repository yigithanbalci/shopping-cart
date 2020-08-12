package tr.com.yigithanbalci.shoppingcartservice.dto.report;

import java.io.Serializable;
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
public class DrinkAndMostUsedTopping implements Serializable {

  private String drink;
  private String mostUsedTopping;
}

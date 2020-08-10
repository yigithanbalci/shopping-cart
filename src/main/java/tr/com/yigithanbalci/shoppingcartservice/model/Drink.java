package tr.com.yigithanbalci.shoppingcartservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Drink {

  private String name;
  private Float price;
}

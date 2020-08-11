package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.util.Objects;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Drink {

  private String name;
  private Float price;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Drink drink = (Drink) o;
    return name.equals(drink.name) &&
        price.equals(drink.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, price);
  }
}

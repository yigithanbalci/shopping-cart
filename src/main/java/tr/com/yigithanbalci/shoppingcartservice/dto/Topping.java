package tr.com.yigithanbalci.shoppingcartservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
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
public class Topping implements Serializable {

  // TODO: 13.08.2020 baseentity
  @JsonIgnore
  private Long id;
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
    Topping topping = (Topping) o;
    return name.equals(topping.name) &&
        price.equals(topping.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, price);
  }
}

package tr.com.yigithanbalci.shoppingcartservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Builder for sake of usage. Temp refactor to Abstract Factory Methods maybe.
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Drink implements Serializable {

  @JsonIgnore
  private Long id;
  private String name;
  private Float price;
}

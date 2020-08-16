package tr.com.yigithanbalci.shoppingcartservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class ToppingInput implements Serializable {

  @JsonProperty(required = true)
  @NotBlank(message = "Name is not blank")
  private final String name;

  @JsonProperty(required = true)
  @Positive(message = "Amount is positive or zero")
  private final BigDecimal amount;

}
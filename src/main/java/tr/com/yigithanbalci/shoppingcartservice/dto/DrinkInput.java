package tr.com.yigithanbalci.shoppingcartservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DrinkInput implements Serializable {

  @NotBlank(message = "Name is not blank")
  private final String name;

  @Positive(message = "Amount is positive or zero")
  private final BigDecimal amount;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public DrinkInput(@JsonProperty("name") String name, @JsonProperty("amount") BigDecimal amount) {
    this.name = name;
    this.amount = amount;
  }
}
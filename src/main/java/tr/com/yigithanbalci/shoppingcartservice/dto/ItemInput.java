package tr.com.yigithanbalci.shoppingcartservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ItemInput {

  @Positive(message = "Drink id is positive")
  private final Long drinkId;

  private final List<Long> toppingIds;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public ItemInput(@JsonProperty("drinkID") Long drinkId,
      @JsonProperty("toppingIds") List<Long> toppingIds) {
    this.drinkId = drinkId;
    this.toppingIds = toppingIds != null ? toppingIds : new ArrayList<>();
  }
}
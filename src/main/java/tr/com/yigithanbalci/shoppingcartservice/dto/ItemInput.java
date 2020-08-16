package tr.com.yigithanbalci.shoppingcartservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ItemInput extends SelfValidating<ItemInput> implements Serializable {

  @NotNull
  @Positive(message = "Drink id is positive")
  private final Long drinkId;

  private final List<Long> toppingIds;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public ItemInput(@JsonProperty(value = "drinkId", required = true) Long drinkId,
       @JsonProperty(value = "toppingIds", required = true) List<Long> toppingIds) {
    this.drinkId = drinkId;
    this.toppingIds = toppingIds != null ? toppingIds : new ArrayList<>();
    this.validateSelf();
  }
}
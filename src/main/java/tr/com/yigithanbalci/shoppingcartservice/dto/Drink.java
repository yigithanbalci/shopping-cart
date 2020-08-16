package tr.com.yigithanbalci.shoppingcartservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Drink extends SelfValidating<Drink> implements Serializable {

  @Positive(message = "Id is positive")
  private final Long id;

  @NotBlank(message = "Name is not blank")
  private final String name;

  @Positive(message = "Amount is positive or zero")
  private final BigDecimal amount;

  public static Drink from(DrinkEntity drinkEntity) {
    return new Drink(drinkEntity.getId(), drinkEntity.getName(), drinkEntity.getAmount());
  }

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public static Drink createWithIdAndNameAndPrice(@JsonProperty("id") Long id,
      @JsonProperty("name") String name,
      @JsonProperty("price") BigDecimal price) {
    Drink drink = new Drink(id, name, price);
    drink.validateSelf();
    return drink;
  }
}

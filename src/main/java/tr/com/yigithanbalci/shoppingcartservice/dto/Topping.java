package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Topping extends SelfValidating<Topping> implements Serializable {

  @Positive(message = "Id is positive")
  private final Long id;

  @NotBlank(message = "Name is not blank")
  private final String name;

  @Positive(message = "Amount is positive or zero")
  private final BigDecimal amount;

  public static Topping from(ToppingEntity toppingEntity){
    return new Topping(toppingEntity.getId(), toppingEntity.getName(), toppingEntity.getAmount());
  }

  public static Topping createWithIdAndNameAndPrice(Long id, String name, BigDecimal price) {
    Topping topping = new Topping(id, name, price);
    topping.validateSelf();
    return topping;
  }
}

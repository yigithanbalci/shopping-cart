package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Item extends SelfValidating<Item> implements Serializable {

  @NonNull
  private Drink drink;

  private List<Topping> toppings = new ArrayList<>();

  @PositiveOrZero(message = "Amount id positive")
  private BigDecimal amount = BigDecimal.ZERO;

  public static Item createWithDrink(@NonNull final Drink drink){
    return new Item(drink);
  }

  protected Item(final Drink drink) {
    this.amount = drink.getAmount();
    this.drink = drink;
    this.validateSelf();
  }

  public void setDrink(@NonNull final Drink drink) {
    this.amount = this.amount.subtract(this.amount);
    this.amount = this.amount.add(drink.getAmount());
    this.drink = drink;
  }

  public void addTopping(@NonNull final Topping topping) {
    toppings.add(topping);
    this.amount = this.amount.add(topping.getAmount());
  }
}

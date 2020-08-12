package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Item implements Serializable {

  private Drink drink;
  private List<Topping> toppings = new ArrayList<>();
  private Float amount = 0f;

  public Item(@NonNull Drink drink) {
    this.drink = drink;
    amount = amount + drink.getPrice();
  }

  public void setDrink(){
    this.drink = drink;
    amount = amount + drink.getPrice();
  }

  public void addTopping(Topping topping) {
    toppings.add(topping);
    amount = amount + topping.getPrice();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Item item = (Item) o;
    return drink.equals(item.drink) &&
        Objects.equals(toppings, item.toppings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(drink, toppings);
  }
}

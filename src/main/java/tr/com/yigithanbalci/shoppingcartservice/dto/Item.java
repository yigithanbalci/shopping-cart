package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Item {

  @NonNull
  private final Drink drink;
  private List<Topping> toppings = new ArrayList<>();

  public void addTopping(Topping topping) {
    toppings.add(topping);
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

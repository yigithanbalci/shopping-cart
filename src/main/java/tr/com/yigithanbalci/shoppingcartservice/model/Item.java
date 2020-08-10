package tr.com.yigithanbalci.shoppingcartservice.model;

import java.util.ArrayList;
import java.util.List;
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
}

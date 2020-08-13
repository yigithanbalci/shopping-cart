package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Item implements Serializable {

  private Drink drink;
  private List<Topping> toppings = new ArrayList<>();
  private Float amount = 0f;

  public Item(final Drink drink) {
    if(drink != null && drink.getPrice() != null){
      amount = drink.getPrice();
    }
    this.drink = drink;
  }

  public void setDrink(final Drink drink){
    if(this.drink != null && this.drink.getPrice() != null){
      amount = amount - this.drink.getPrice();
    }
    if(drink != null && drink.getPrice() != null){
      amount = amount + drink.getPrice();
    }
    this.drink = drink;
  }

  public void addTopping(Topping topping) {
    if (topping != null){
      toppings.add(topping);
      amount = amount + topping.getPrice();
    }
  }
}

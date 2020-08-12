package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Cart implements Serializable {

  private List<Item> items = new ArrayList<>();
  private Float amount = 0.0f;

  public void addItem(Item item){
    items.add(item);
    amount = amount + item.getAmount();
  }

  public void deleteItem(Item item){
    items.remove(item);
    amount = amount - item.getAmount();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cart cart = (Cart) o;
    return items.equals(cart.items) &&
        amount.equals(cart.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, amount);
  }
}

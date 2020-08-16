package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class Cart implements Serializable {

  @NonNull
  private List<Item> items = new ArrayList<>();

  private BigDecimal amount = BigDecimal.ZERO;

  public static Cart createWithItems(@NonNull List<Item> items){
    Cart cart = new Cart();
    items.forEach(cart::addItem);
    return cart;
  }

  public void addItem(@NonNull Item item){
    items.add(item);
    amount = amount.add(item.getAmount());
  }

  public void deleteItem(@NonNull Item item){
    items.remove(item);
    amount = amount.subtract(item.getAmount());
  }
}

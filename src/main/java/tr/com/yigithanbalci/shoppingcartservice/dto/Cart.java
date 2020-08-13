package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}

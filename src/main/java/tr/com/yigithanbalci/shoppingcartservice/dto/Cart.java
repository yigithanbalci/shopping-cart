package tr.com.yigithanbalci.shoppingcartservice.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

  private List<Item> items = new ArrayList<>();

  public void addItem(Item item){
    items.add(item);
  }
}

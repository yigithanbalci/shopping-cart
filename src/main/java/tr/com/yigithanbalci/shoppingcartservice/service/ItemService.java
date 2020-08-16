package tr.com.yigithanbalci.shoppingcartservice.service;

import lombok.NonNull;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;

public interface ItemService {

  Item getItemFromItemInput(@NonNull final ItemInput itemInput);
}

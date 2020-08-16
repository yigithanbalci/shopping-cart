package tr.com.yigithanbalci.shoppingcartservice.service;

import lombok.NonNull;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;

public interface ShoppingService {

  Cart addItemToCart(@NonNull final ItemInput item, @NonNull final Long userId);
  Cart deleteItemFromCart(@NonNull final ItemInput item, @NonNull final Long userId);
  FinalizedCart checkoutCart(@NonNull final Long userId);
}

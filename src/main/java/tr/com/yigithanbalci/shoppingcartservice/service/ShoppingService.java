package tr.com.yigithanbalci.shoppingcartservice.service;

import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;

public interface ShoppingService {

  Cart addItemToCart(ItemInput item, Long userId);
  Cart deleteItemFromCart(ItemInput item, Long userId);
  FinalizedCart checkoutCart(Long userId);
}

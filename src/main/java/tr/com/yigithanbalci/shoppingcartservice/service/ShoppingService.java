package tr.com.yigithanbalci.shoppingcartservice.service;

import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;

public interface ShoppingService {

  Cart addItemToCart(Item item, Long userId);
  Cart deleteItemFromCart(Item item, Long userId);
  FinalizedCart checkoutCart(Long userId);
}

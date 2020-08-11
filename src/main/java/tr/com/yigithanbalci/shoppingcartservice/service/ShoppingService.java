package tr.com.yigithanbalci.shoppingcartservice.service;

import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;

public interface ShoppingService {

  Cart addItemToCart(Item item, Long usedId);
  Cart deleteItemFromCart(Item item, Long usedId);
  Cart checkoutCart(Long usedId);
}

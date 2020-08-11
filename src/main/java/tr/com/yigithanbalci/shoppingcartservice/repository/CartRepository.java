package tr.com.yigithanbalci.shoppingcartservice.repository;

import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;

public interface CartRepository {

  Cart findByUserId(Long userId);
  Cart updateByUserId(Cart cart, Long userId);
}

package tr.com.yigithanbalci.shoppingcartservice.repository;

import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;

public interface CartRepository {

  Cart findByUserId(Long userId);
  void updateByUserId(Cart cart, Long userId);
  void deleteByUserId(Long userId);
}

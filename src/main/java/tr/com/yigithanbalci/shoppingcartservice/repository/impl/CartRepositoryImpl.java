package tr.com.yigithanbalci.shoppingcartservice.repository.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.repository.CartRepository;

@Repository
public class CartRepositoryImpl implements CartRepository {

  private Map<Long, Cart> inMemoryCartMap = new HashMap<>();

  @Override
  public Cart findByUserId(Long userId) {
    return null;
  }

  @Override
  public Cart updateByUserId(Cart cart, Long userId) {
    return null;
  }
}

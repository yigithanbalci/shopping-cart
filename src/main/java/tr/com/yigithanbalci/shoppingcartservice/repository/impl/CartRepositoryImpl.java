package tr.com.yigithanbalci.shoppingcartservice.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.repository.CartRepository;

@Repository
public class CartRepositoryImpl implements CartRepository {

  private Map<Long, Cart> inMemoryCartMap = new ConcurrentHashMap<>();

  @Override
  public Cart findByUserId(Long userId) {
    Cart cart = inMemoryCartMap.get(userId);
    if(cart == null){
      cart = new Cart();
      inMemoryCartMap.put(userId, cart);
    }
    return cart;
  }

  @Override
  public void updateByUserId(Cart cart, Long userId) {
    inMemoryCartMap.put(userId, cart);
  }

  @Override
  public void deleteByUserId(Long userId) {
    inMemoryCartMap.remove(userId);
  }
}

package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingServiceImpl implements ShoppingService {

  private Map<Long, Cart> inMemoryCartMap = new HashMap<>();

  @Override
  public Cart addItemToCart(Item item, Long usedId) {
    return null;
  }

  @Override
  public Cart deleteItemFromCart(Item item, Long usedId) {
    return null;
  }

  @Override
  public Cart checkoutCart(Long usedId) {
    return null;
  }
}

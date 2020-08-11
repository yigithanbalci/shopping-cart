package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.repository.CartRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.CustomerRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingServiceImpl implements ShoppingService {

  @NonNull
  private final CartRepository cartRepository;

  @NonNull
  private final CustomerRepository customerRepository;

  @Override
  public Cart addItemToCart(Item item, Long userId) {
    log.info("Started to add item to cart of user with id: " + userId);
    Cart cart = cartRepository.findByUserId(userId);
    cart.addItem(item);
    cartRepository.updateByUserId(cart, userId);
    log.info("Finished to add item to cart of user with id: " + userId);
    return cart;
  }

  @Override
  public Cart deleteItemFromCart(Item item, Long userId) {
    log.info("Started to delete item to cart of user with id: " + userId);
    Cart cart = cartRepository.findByUserId(userId);
    cart.deleteItem(item);
    cartRepository.updateByUserId(cart, userId);
    log.info("Finished to delete item to cart of user with id: " + userId);
    return cart;
  }

  @Override
  public FinalizedCart checkoutCart(Long userId) {
    log.info("Started to checkout cart of user with id: " + userId);
    Cart cart = cartRepository.findByUserId(userId);
    cartRepository.deleteByUserId(userId);
    FinalizedCart finalizedCart = FinalizedCart.builder().originalAmount(cart.getAmount())
        .discountedAmount(calculateDiscountedAmount(cart)).build();
    log.info("Finished to checkout cart of user with id: " + userId);
    return finalizedCart;
  }

  private float calculateDiscountedAmount(Cart cart) {
    float discountedAmount =
        cart.getAmount() > 12.0f ? (cart.getAmount() * 0.75f) : cart.getAmount();
    if (cart.getItems().size() >= 3) {
      List<Float> amounts = cart.getItems().stream().map(Item::getAmount)
          .collect(Collectors.toList());
      amounts.add(discountedAmount);
      discountedAmount = Collections.min(amounts);
    }
    return discountedAmount;
  }
}

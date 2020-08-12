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
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkToppingRelation;
import tr.com.yigithanbalci.shoppingcartservice.repository.CartRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkToppingRelationRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingServiceImpl implements ShoppingService {

  @NonNull
  private final CartRepository cartRepository;

  @NonNull
  private final DrinkToppingRelationRepository drinkToppingRelationRepository;

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
    FinalizedCart finalizedCart = FinalizedCart.builder().originalAmount(cart.getAmount())
        .discountedAmount(calculateDiscountedAmount(cart)).build();
    cartRepository.deleteByUserId(userId);
    updateDrinkToppingRelation(cart);
    log.info("Finished to checkout cart of user with id: " + userId);
    return finalizedCart;
  }

  private void updateDrinkToppingRelation(Cart cart) {
    cart.getItems().forEach(item -> item.getToppings().forEach(topping -> {
      DrinkToppingRelation relation = drinkToppingRelationRepository
          .findByDrinkIdEqualsAndToppingIdEquals(item.getDrink().getId(), topping.getId());
      if(relation == null){
        relation = new DrinkToppingRelation();
        relation.setDrinkId(item.getDrink().getId());
        relation.setToppingId(topping.getId());
        relation.setAmount(0L);
      }
      relation.setAmount(relation.getAmount() + 1);
      drinkToppingRelationRepository.save(relation);
    }));
  }

  private float calculateDiscountedAmount(Cart cart) {
    float discountedAmount =
        cart.getAmount() > 12.0f ? (cart.getAmount() * 0.75f) : cart.getAmount();
    if (cart.getItems().size() >= 3) {
      List<Float> amounts = cart.getItems().stream().map(Item::getAmount)
          .collect(Collectors.toList());
      float minAmount = Collections.min(amounts);
      if (cart.getAmount() - minAmount < discountedAmount) {
        discountedAmount = cart.getAmount() - minAmount;
      }
    }
    return discountedAmount;
  }
}

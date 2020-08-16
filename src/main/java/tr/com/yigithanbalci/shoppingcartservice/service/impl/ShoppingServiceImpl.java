package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;
import tr.com.yigithanbalci.shoppingcartservice.model.Customer;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkToppingRelation;
import tr.com.yigithanbalci.shoppingcartservice.repository.CartRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.CustomerRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkToppingRelationRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ItemService;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingServiceImpl implements ShoppingService {

  private final ItemService itemService;
  private final CartRepository cartRepository;
  private final CustomerRepository customerRepository;
  private final DrinkToppingRelationRepository drinkToppingRelationRepository;

  @Override
  public Cart addItemToCart(ItemInput item, Long userId) {
    log.debug("Started to add item to cart of user with id: " + userId);
    Cart cart = cartRepository.findByUserId(userId);
    cart.addItem(itemService.getItemFromItemInput(item));
    cartRepository.updateByUserId(cart, userId);
    log.debug("Finished to add item to cart of user with id: " + userId);
    return cart;
  }

  @Override
  public Cart deleteItemFromCart(ItemInput item, Long userId) {
    log.debug("Started to delete item to cart of user with id: " + userId);
    Cart cart = cartRepository.findByUserId(userId);
    cart.deleteItem(itemService.getItemFromItemInput(item));
    cartRepository.updateByUserId(cart, userId);
    log.debug("Finished to delete item to cart of user with id: " + userId);
    return cart;
  }

  @Override
  public FinalizedCart checkoutCart(Long userId) {
    log.debug("Started to checkout cart of user with id: " + userId);
    Cart cart = cartRepository.findByUserId(userId);
    FinalizedCart finalizedCart = FinalizedCart
        .createWithOriginalAmountAndDiscountedAmount(cart.getAmount(),
            calculateDiscountedAmount(cart));
    cartRepository.deleteByUserId(userId);
    updateDrinkToppingRelation(cart);
    Customer customer = customerRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + userId));
    customer.setTotalAmountOfOrders(customer.getTotalAmountOfOrders().add(BigDecimal.ONE));
    customerRepository.save(customer);
    log.debug("Finished to checkout cart of user with id: " + userId);
    return finalizedCart;
  }

  private void updateDrinkToppingRelation(Cart cart) {
    cart.getItems().forEach(item -> item.getToppings().forEach(topping -> {
      DrinkToppingRelation relation = drinkToppingRelationRepository
          .findByDrinkIdEqualsAndToppingIdEquals(item.getDrink().getId(), topping.getId());
      if (relation == null) {
        relation = new DrinkToppingRelation();
        relation.setDrinkId(item.getDrink().getId());
        relation.setToppingId(topping.getId());
        relation.setNumberOfUsageTogether(0L);
      }
      relation.usedOneMoreTime();
      drinkToppingRelationRepository.save(relation);
    }));
  }

  private BigDecimal calculateDiscountedAmount(Cart cart) {
    BigDecimal discountedAmount =
        cart.getAmount().compareTo(BigDecimal.valueOf(12.0)) > 0 ? (cart.getAmount()
            .multiply(BigDecimal.valueOf(0.75))) : cart.getAmount();
    if (cart.getItems().size() >= 3) {
      List<BigDecimal> amounts = cart.getItems().stream().map(Item::getAmount)
          .collect(Collectors.toList());
      BigDecimal minAmount = Collections.min(amounts);
      BigDecimal substracted = cart.getAmount().subtract(minAmount);
      if (discountedAmount.compareTo(substracted) > 0) {
        discountedAmount = cart.getAmount().subtract(minAmount);
      }
    }
    return discountedAmount;
  }
}

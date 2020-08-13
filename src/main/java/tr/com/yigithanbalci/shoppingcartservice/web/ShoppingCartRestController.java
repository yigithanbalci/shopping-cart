package tr.com.yigithanbalci.shoppingcartservice.web;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.yigithanbalci.shoppingcartservice.auth.UserDetailsImpl;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.exception.AuthorizationException;
import tr.com.yigithanbalci.shoppingcartservice.exception.CustomerNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.exception.InternalServerException;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ShoppingCartRestController {

  private final ShoppingService shoppingService;

  @PutMapping("/{userId}/cart")
  public ResponseEntity<Cart> addItemToCart(final Principal principal, @RequestBody final Item item,
      @PathVariable final Long userId) {
    try {
      checkAuthentication(principal, userId);
      Cart cart = shoppingService.addItemToCart(item, userId);
      return ResponseEntity.ok(cart);
    } catch (AuthorizationException e){
      log.error("Authorization error: {} ", e.getLocalizedMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error("Exception occurs while reporting toppings by drink {}", e.getLocalizedMessage(), e);
      throw new InternalServerException("Toppings by drink could not be reported: " + e.getLocalizedMessage());
    }
  }

  @DeleteMapping("/{userId}/cart")
  public ResponseEntity<Cart> deleteItemFromCart(final Principal principal, @RequestBody final Item item,
      @PathVariable final Long userId) {
    try {
      checkAuthentication(principal, userId);
      Cart cart = shoppingService.deleteItemFromCart(item, userId);
      return ResponseEntity.ok(cart);
    } catch (AuthorizationException e){
      log.error("Authorization error: {} ", e.getLocalizedMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error("Exception occurs while reporting toppings by drink {}", e.getLocalizedMessage(), e);
      throw new InternalServerException("Toppings by drink could not be reported: " + e.getLocalizedMessage());
    }
  }

  @PutMapping("/{userId}/cart/checkout")
  public ResponseEntity<FinalizedCart> checkoutShoppingCart(final Principal principal, @PathVariable final Long userId) {
    try {
      checkAuthentication(principal, userId);
      FinalizedCart finalizedCart = shoppingService.checkoutCart(userId);
      return ResponseEntity.ok(finalizedCart);
    } catch (AuthorizationException e){
      log.error("Authorization error: {} ", e.getLocalizedMessage(), e);
      throw e;
    } catch (CustomerNotFoundException e){
      log.error("Customer not found with id {}: {} ", userId, e.getLocalizedMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error("Exception occurs while reporting toppings by drink {}", e.getLocalizedMessage(), e);
      throw new InternalServerException("Toppings by drink could not be reported: " + e.getLocalizedMessage());
    }
  }

  private void checkAuthentication(final Principal principal, final Long userId) {
    Long principalUserId = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal)
        .getPrincipal()).getUserId();

    if (!principalUserId.equals(userId)) {
      throw new AuthorizationException(
          "Authorized user is not equal to the user in request: " + principalUserId + " - "
              + userId);
    }

  }
}

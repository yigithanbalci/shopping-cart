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
import tr.com.yigithanbalci.shoppingcartservice.exception.AuthorizationException;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ShoppingCartRestController {

  @PutMapping("/{userId}/cart")
  public ResponseEntity addItemToCart(Principal principal, @RequestBody String item,
      @PathVariable("userId") String userIdStr) {
    Long userId = Long.parseLong(userIdStr);
    checkAuthentication(principal, userId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{userId}/cart")
  public ResponseEntity deleteItemFromCart(Principal principal, @RequestBody String item,
      @PathVariable("userId") String userIdStr) {
    Long userId = Long.parseLong(userIdStr);
    checkAuthentication(principal, userId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{userId}/cart/checkout")
  public ResponseEntity checkoutShoppingCart(Principal principal, @PathVariable("userId") String userIdStr) {
    Long userId = Long.parseLong(userIdStr);
    checkAuthentication(principal, userId);
    return ResponseEntity.ok().build();
  }

  private void checkAuthentication(Principal principal, Long userId) {
    Long principalUserId = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal)
        .getPrincipal()).getUserId();

    if (!principalUserId.equals(userId)) {
      throw new AuthorizationException(
          "Authorized user is not equal to the user in request: " + principalUserId + " - "
              + userId);
    }

  }
}

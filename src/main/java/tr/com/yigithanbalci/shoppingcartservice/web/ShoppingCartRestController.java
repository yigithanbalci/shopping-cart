package tr.com.yigithanbalci.shoppingcartservice.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.security.Principal;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;
import tr.com.yigithanbalci.shoppingcartservice.exception.AuthorizationException;
import tr.com.yigithanbalci.shoppingcartservice.security.UserDetailsImpl;
import tr.com.yigithanbalci.shoppingcartservice.service.ShoppingService;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ShoppingCartRestController {

  private final ShoppingService shoppingService;

  @Operation(summary = "Add item to Cart.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item added to cart and cart returned.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Cart.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized or authentication and path variable are not the same.",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @PostMapping("/{userId}/cart")
  public ResponseEntity<Cart> addItemToCart(final Principal principal,
      @RequestBody @Valid final ItemInput item,
      @PathVariable @Positive(message = "User id is positive or zero") final Long userId) {
      checkAuthentication(principal, userId);
      Cart cart = shoppingService.addItemToCart(item, userId);
      return ResponseEntity.ok(cart);
  }

  @Operation(summary = "Delete item from Cart.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item deleted from cart and cart returned.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Cart.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized or authentication and path variable are not the same.",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @PutMapping("/{userId}/cart")
  public ResponseEntity<Cart> deleteItemFromCart(final Principal principal,
      @RequestBody @Valid final ItemInput item,
      @PathVariable @Positive(message = "User id is positive or zero") final Long userId) {
      checkAuthentication(principal, userId);
      Cart cart = shoppingService.deleteItemFromCart(item, userId);
      return ResponseEntity.ok(cart);
  }

  @Operation(summary = "Checkout the cart.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cart checked out, total amount and discount amount returned.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = FinalizedCart.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized or authentication and path variable are not the same.",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Customer not found.",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @GetMapping("/{userId}/cart/checkout")
  public ResponseEntity<FinalizedCart> checkoutShoppingCart(final Principal principal,
      @PathVariable @NonNull @Positive(message = "User id is positive or zero") final Long userId) {
    checkAuthentication(principal, userId);
    FinalizedCart finalizedCart = shoppingService.checkoutCart(userId);
    return ResponseEntity.ok(finalizedCart);
  }

  private void checkAuthentication(final Principal principal, final Long userId) {
    Long principalUserId = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal)
        .getPrincipal()).getCustomerId();

    if (!principalUserId.equals(userId)) {
      throw new AuthorizationException(
          "Authorized user is not equal to the user in request: " + principalUserId + " - "
              + userId);
    }

  }
}

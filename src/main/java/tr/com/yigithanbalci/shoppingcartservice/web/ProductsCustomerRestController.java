package tr.com.yigithanbalci.shoppingcartservice.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsCustomerRestController {

  private final DrinkService drinkService;
  private final ToppingService toppingService;

  @Operation(summary = "Get list of all toppings.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found toppings.",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = Topping.class)))}),
      @ApiResponse(responseCode = "404", description = "Toppings not found.",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @GetMapping("/toppings")
  public ResponseEntity<List<Topping>> getAllToppings(){
      List<Topping> toppings = toppingService.findAll();
      return ResponseEntity.ok(toppings);
  }

  @Operation(summary = "Get list of all drinks.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found drinks.",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = Drink.class)))}),
      @ApiResponse(responseCode = "404", description = "Drinks not found.",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @GetMapping("/drinks")
  public ResponseEntity<List<Drink>> getAllDrinks(){
      List<Drink> drinks = drinkService.findAll();
      return ResponseEntity.ok(drinks);
  }
}

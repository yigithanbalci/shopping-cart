package tr.com.yigithanbalci.shoppingcartservice.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.DrinkInput;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.dto.ToppingInput;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductsAdminRestController {

  private final DrinkService drinkService;
  private final ToppingService toppingService;

  @Operation(summary = "Create a drink.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Created drink.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = DrinkEntity.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @PostMapping("/drinks")
  public ResponseEntity<Drink> createDrink(@Valid @RequestBody final DrinkInput drink) {
      Drink createdDrink = drinkService.create(drink);
      return ResponseEntity.ok(createdDrink);
  }

  @Operation(summary = "Create a topping.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Created topping.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ToppingEntity.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @PostMapping("/toppings")
  public ResponseEntity<Topping> createTopping(
      @Valid @RequestBody final ToppingInput topping) {
      Topping createdTopping = toppingService.create(topping);
      return ResponseEntity.ok(createdTopping);
  }

  @Operation(summary = "Update a drink.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Updated drink.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = DrinkEntity.class))}),
      @ApiResponse(responseCode = "404", description = "Drink not found.",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @PutMapping("/drinks/{drinkId}")
  public ResponseEntity<Drink> updateDrink(@Valid @RequestBody final DrinkInput drink,
      @PathVariable @Positive(message = "Drink id is positive") final Long drinkId) {
    Drink updatedDrink = drinkService
        .update(Drink.createWithIdAndNameAndPrice(drinkId, drink.getName(), drink.getAmount()));
    return ResponseEntity.ok(updatedDrink);
  }

  @Operation(summary = "Update a topping.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Updated topping.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ToppingEntity.class))}),
      @ApiResponse(responseCode = "404", description = "Topping not found.",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @PutMapping("/toppings/{toppingId}")
  public ResponseEntity<Topping> updateTopping(@Valid @RequestBody final ToppingInput topping,
      @PathVariable @Positive(message = "Drink id is positive") final Long toppingId) {
    Topping updatedTopping = toppingService.update(
        Topping.createWithIdAndNameAndPrice(toppingId, topping.getName(), topping.getAmount()));
    return ResponseEntity.ok(updatedTopping);
  }

  @Operation(summary = "Delete a drink.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deleted drink.",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @DeleteMapping("/drinks/{drinkId}")
  public ResponseEntity<Void> deleteDrink(
      @PathVariable @Positive(message = "Drink id is positive or zero") final Long drinkId) {
    drinkService.delete(drinkId);
    return ResponseEntity.ok().build();

  }

  @Operation(summary = "Delete a topping.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deleted topping.",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal Server Error Occured.",
          content = @Content)
  })
  @DeleteMapping("/toppings/{toppingId}")
  public ResponseEntity<Void> deleteTopping(
      @PathVariable @Positive(message = "Topping id is positive or zero") final Long toppingId) {
      toppingService.delete(toppingId);
    return ResponseEntity.ok().build();
  }
}

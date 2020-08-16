package tr.com.yigithanbalci.shoppingcartservice.dto.report;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tr.com.yigithanbalci.shoppingcartservice.dto.SelfValidating;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DrinkAndMostUsedTopping extends SelfValidating<DrinkAndMostUsedTopping> implements Serializable {

  @NotBlank(message = "Drink is not blank")
  private String drink;

  @NotBlank(message = "Most used Topping with the drink is not blank")
  private String mostUsedTopping;

  public static DrinkAndMostUsedTopping createWithDrinkAndMostUsedTopping(String drink,
      String mostUsedTopping) {
    DrinkAndMostUsedTopping drinkAndMostUsedTopping = new DrinkAndMostUsedTopping(drink, mostUsedTopping);
    drinkAndMostUsedTopping.validateSelf();
    return drinkAndMostUsedTopping;
  }
}

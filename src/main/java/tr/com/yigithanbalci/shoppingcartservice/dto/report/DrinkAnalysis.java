package tr.com.yigithanbalci.shoppingcartservice.dto.report;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DrinkAnalysis {

  private List<DrinkAndMostUsedToppingWrapper> drinks = new ArrayList<>();

  public void addDrinksAndToppings(DrinkAndMostUsedToppingWrapper drinkAndMostUsedToppingWrapper){
    drinks.add(drinkAndMostUsedToppingWrapper);
  }
}

package tr.com.yigithanbalci.shoppingcartservice.dto.report;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrinkAnalysis {

  private List<DrinkAndMostUsedToppingWrapper> drinks = new ArrayList<>();

  public void addDrinksAndToppings(DrinkAndMostUsedToppingWrapper drinkAndMostUsedToppingWrapper){
    drinks.add(drinkAndMostUsedToppingWrapper);
  }
}

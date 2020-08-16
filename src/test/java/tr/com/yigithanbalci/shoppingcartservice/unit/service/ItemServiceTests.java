package tr.com.yigithanbalci.shoppingcartservice.unit.service;



import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ItemService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.ItemServiceImpl;

@ExtendWith(SpringExtension.class)
public class ItemServiceTests {

  private ItemService itemService;

  @MockBean
  private DrinkService drinkService;

  @MockBean
  private ToppingService toppingService;

  @BeforeEach
  public void setUp() {
    itemService = new ItemServiceImpl(drinkService, toppingService);
  }

  @Test
  public void whenItemInputWithDrinkAndToppingId_thenItemWithDrinkAndToppingFromDatabase(){
    DrinkEntity blackCoffee = new DrinkEntity(1L, "Black Coffee", BigDecimal.valueOf(4.0));
    ToppingEntity mapleSyrup = new ToppingEntity(5L, "Maple syrup", BigDecimal.valueOf(3.0));
    List<Long> toppingIdList = new ArrayList<>();
    toppingIdList.add(mapleSyrup.getId());

    Item item = Item.createWithDrink(Drink.from(blackCoffee));
    item.addTopping(Topping.from(mapleSyrup));

    Mockito.when(drinkService.findById(blackCoffee.getId())).thenReturn(Drink.from(blackCoffee));
    Mockito.when(toppingService.findById(mapleSyrup.getId())).thenReturn(Topping.from(mapleSyrup));

    Item itemFromItemInput = itemService
        .getItemFromItemInput(new ItemInput(blackCoffee.getId(), toppingIdList));
    assertThat(item).isEqualTo(itemFromItemInput);
  }
}
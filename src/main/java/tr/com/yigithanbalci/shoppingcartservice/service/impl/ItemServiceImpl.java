package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.ItemInput;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.ItemService;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  private final DrinkService drinkService;
  private final ToppingService toppingService;

  @Override
  public Item getItemFromItemInput(ItemInput itemInput) {
    Item item = Item.createWithDrink(drinkService.findById(itemInput.getDrinkId()));
    itemInput.getToppingIds().forEach(toppingId -> item.addTopping(toppingService.findById(toppingId)));
    return item;
  }
}

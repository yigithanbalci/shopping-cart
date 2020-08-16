package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.DrinkInput;
import tr.com.yigithanbalci.shoppingcartservice.exception.DrinkNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.DrinkService;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.DrinkServiceImpl;

@ExtendWith(SpringExtension.class)
public class DrinkServiceTests {

  private DrinkService drinkService;

  @MockBean
  private DrinkRepository drinkRepository;

  @BeforeEach
  public void setUp(){
    drinkService = new DrinkServiceImpl(drinkRepository);
  }

  @Test
  public void whenNotFound_thenExceptionShouldBeThrown(){
    Mockito.when(drinkRepository.findAll()).thenReturn(new ArrayList<>());
    assertThatExceptionOfType(DrinkNotFoundException.class).isThrownBy(() -> drinkService.findAll());
  }

  @Test
  public void whenValidName_thenDrinkShouldBeFound(){
    DrinkEntity blackCoffee = new DrinkEntity(1L, "Black Coffee", BigDecimal.valueOf(4.0));
    DrinkEntity latte = new DrinkEntity(2L, "Latte", BigDecimal.valueOf(5.0));
    DrinkEntity mocha = new DrinkEntity(3L, "Mocha", BigDecimal.valueOf(6.0));
    DrinkEntity tea = new DrinkEntity(4L, "Tea", BigDecimal.valueOf(3.0));

    List<DrinkEntity> drinks = new ArrayList<>();
    drinks.add(blackCoffee);
    drinks.add(latte);
    drinks.add(mocha);
    drinks.add(tea);

    Mockito.when(drinkRepository.findAll()).thenReturn(drinks);

    String name = "Latte";
    Drink java = Drink.createWithIdAndNameAndPrice(5L, "Java", BigDecimal.valueOf(7.0));
    Drink found = drinkService.findAll().stream().filter(drinkEntity -> drinkEntity.getId() == 2L).findFirst().orElse(java);

    assertThat(found.getName()).isEqualTo(name);
  }

  // These functions below directly comes from jpa repo.
  @Test
  public void crudTest(){
    Drink java = Drink.createWithIdAndNameAndPrice(5L, "Java", BigDecimal.valueOf(7.0));
    Drink updatedJava = Drink.createWithIdAndNameAndPrice(5L, "Java", BigDecimal.valueOf(5.0));
    Mockito.when(drinkRepository.findById(5L)).thenReturn(Optional.of(DrinkEntity.from(java)));
    Mockito.when(drinkRepository.save(DrinkEntity.from(java))).thenReturn(DrinkEntity.from(java));

    Drink savedDrink = drinkService.create(new DrinkInput(java.getName(), java.getAmount()));
    Mockito.verify(drinkRepository, Mockito.times(1)).save(DrinkEntity.from(java));
    assertThat(savedDrink.getName()).isEqualTo(java.getName());

    Mockito.when(drinkRepository.save(DrinkEntity.from(updatedJava))).thenReturn(DrinkEntity.from(updatedJava));
    Drink updatedDrink = drinkService.update(updatedJava);
    Mockito.verify(drinkRepository, Mockito.times(1)).save(DrinkEntity.from(java));
    assertThat(updatedDrink.getAmount()).isEqualTo(updatedJava.getAmount());

    drinkService.delete(java.getId());
    Mockito.verify(drinkRepository, Mockito.times(1)).deleteById(java.getId());
  }
}

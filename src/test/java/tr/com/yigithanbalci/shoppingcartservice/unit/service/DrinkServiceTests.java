package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
    DrinkEntity blackCoffee = DrinkEntity.builder().id(1L).name("Black Coffee").price(4.0f).build();
    DrinkEntity latte = DrinkEntity.builder().id(2L).name("Latte").price(5.0f).build();
    DrinkEntity mocha = DrinkEntity.builder().id(3L).name("Mocha").price(6.0f).build();
    DrinkEntity tea = DrinkEntity.builder().id(4L).name("Tea").price(3.0f).build();

    List<DrinkEntity> drinks = new ArrayList<>();
    drinks.add(blackCoffee);
    drinks.add(latte);
    drinks.add(mocha);
    drinks.add(tea);

    Mockito.when(drinkRepository.findAll()).thenReturn(drinks);

    String name = "Latte";
    DrinkEntity java = DrinkEntity.builder().id(5L).name("Java").price(7.0f).build();
    DrinkEntity found = drinkService.findAll().stream().filter(drinkEntity -> drinkEntity.getId().equals(2L)).findFirst().orElse(java);

    assertThat(found.getName()).isEqualTo(name);
  }

  // These functions below directly comes from jpa repo.
  @Test
  public void crudTest(){
    DrinkEntity java = DrinkEntity.builder().id(5L).name("Java").price(7.0f).build();
    DrinkEntity updatedJava = DrinkEntity.builder().id(5L).name("Java").price(5.0f).build();
    Mockito.when(drinkRepository.findById(5L)).thenReturn(Optional.of(java));
    Mockito.when(drinkRepository.save(java)).thenReturn(java);

    DrinkEntity savedDrink = drinkService.create(java);
    Mockito.verify(drinkRepository, Mockito.times(1)).save(java);
    assertThat(savedDrink.getName()).isEqualTo(java.getName());

    java.setId(5L);
    java.setPrice(5.0f);
    Mockito.when(drinkRepository.save(updatedJava)).thenReturn(updatedJava);
    DrinkEntity updatedDrink = drinkService.update(java);
    Mockito.verify(drinkRepository, Mockito.times(2)).save(java);
    assertThat(updatedDrink.getPrice()).isEqualTo(updatedJava.getPrice());

    java.setId(5L);
    drinkService.delete(java.getId());
    Mockito.verify(drinkRepository, Mockito.times(1)).deleteById(java.getId());
  }
}

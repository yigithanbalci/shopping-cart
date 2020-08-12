package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tr.com.yigithanbalci.shoppingcartservice.exception.ToppingNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.ToppingRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.ToppingServiceImpl;

@RunWith(SpringRunner.class)
public class ToppingServiceTests {

  private ToppingService toppingService;

  @MockBean
  private ToppingRepository toppingRepository;

  @Before
  public void setUp() {
    toppingService = new ToppingServiceImpl(toppingRepository);
  }

  @Test(expected = ToppingNotFoundException.class)
  public void whenNotFound_thenExceptionShouldBeThrown(){
    Mockito.when(toppingRepository.findAll()).thenReturn(new ArrayList<>());
    toppingService.findAll();
  }

  @Test
  public void whenValidName_thenToppingShouldBeFound() {
    ToppingEntity milk = ToppingEntity.builder().id(1L).name("Milk").price(2.0f).build();
    ToppingEntity hazelnutSyrup = ToppingEntity.builder().id(2L).name("Hazelnut syrup").price(3.0f)
        .build();
    ToppingEntity chocolateSauce = ToppingEntity.builder().id(3L).name("Chocolate sauce").price(5.0f)
        .build();
    ToppingEntity lemon = ToppingEntity.builder().id(4L).name("Lemon").price(2.0f).build();

    List<ToppingEntity> toppings = new ArrayList<>();
    toppings.add(milk);
    toppings.add(hazelnutSyrup);
    toppings.add(chocolateSauce);
    toppings.add(lemon);

    Mockito.when(toppingRepository.findAll()).thenReturn(toppings);

    String name = "Milk";
    ToppingEntity mapleSyrup = ToppingEntity.builder().id(5L).name("Maple syrup").price(3.0f)
        .build();
    ToppingEntity found = toppingService.findAll().stream()
        .filter(toppingEntity -> toppingEntity.getId().equals(1L)).findFirst().orElse(mapleSyrup);

    assertThat(found.getName()).isEqualTo(name);
  }

  // These functions below directly comes from jpa repo.
  @Test
  public void crudTest() {
    ToppingEntity mapleSyrup = ToppingEntity.builder().id(5L).name("Maple syrup").price(3.0f)
        .build();
    ToppingEntity updatedMapleSyrup = ToppingEntity.builder().id(5L).name("Maple syrup").price(4.0f)
        .build();
    Mockito.when(toppingRepository.findById(5L)).thenReturn(Optional.of(mapleSyrup));
    Mockito.when(toppingRepository.save(mapleSyrup)).thenReturn(mapleSyrup);

    ToppingEntity savedTopping = toppingService.createTopping(mapleSyrup);
    Mockito.verify(toppingRepository, Mockito.times(1)).save(mapleSyrup);
    assertThat(savedTopping.getName()).isEqualTo(mapleSyrup.getName());

    mapleSyrup.setId(5L);
    mapleSyrup.setPrice(4.0f);
    Mockito.when(toppingRepository.save(updatedMapleSyrup)).thenReturn(updatedMapleSyrup);

    ToppingEntity updatedTopping = toppingService.updateTopping(mapleSyrup);
    Mockito.verify(toppingRepository, Mockito.times(2)).save(mapleSyrup);
    assertThat(updatedTopping.getPrice()).isEqualTo(updatedMapleSyrup.getPrice());

    mapleSyrup.setId(5L);
    toppingService.deleteTopping(mapleSyrup.getId());
    Mockito.verify(toppingRepository, Mockito.times(1)).deleteById(mapleSyrup.getId());
  }
}

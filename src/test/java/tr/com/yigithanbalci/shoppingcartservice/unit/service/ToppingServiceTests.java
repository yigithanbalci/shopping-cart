package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.dto.ToppingInput;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.repository.ToppingRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ToppingService;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.ToppingServiceImpl;

@ExtendWith(SpringExtension.class)
public class ToppingServiceTests {

  private ToppingService toppingService;

  @MockBean
  private ToppingRepository toppingRepository;

  @BeforeEach
  public void setUp() {
    toppingService = new ToppingServiceImpl(toppingRepository);
  }

  @Test
  public void whenNotFound_thenExceptionShouldBeThrown() {
    Mockito.when(toppingRepository.findById(1L)).thenReturn(Optional.empty());
    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> toppingService.findById(1L));
  }

  @Test
  public void whenValidName_thenToppingShouldBeFound() {
    ToppingEntity milk = new ToppingEntity(1L, "Milk", BigDecimal.valueOf(2.0));
    ToppingEntity hazelnutSyrup = new ToppingEntity(2L, "Hazelnut syrup", BigDecimal.valueOf(3.0));
    ToppingEntity chocolateSauce = new ToppingEntity(3L, "Chocolate sauce",
        BigDecimal.valueOf(5.0));
    ToppingEntity lemon = new ToppingEntity(4L, "Lemon", BigDecimal.valueOf(2.0));

    List<ToppingEntity> toppings = new ArrayList<>();
    toppings.add(milk);
    toppings.add(hazelnutSyrup);
    toppings.add(chocolateSauce);
    toppings.add(lemon);

    Mockito.when(toppingRepository.findAll()).thenReturn(toppings);

    String name = "Milk";
    Topping mapleSyrup = Topping
        .createWithIdAndNameAndPrice(5L, "Maple syrup", BigDecimal.valueOf(3.0));
    Topping found = toppingService.findAll().stream()
        .filter(toppingEntity -> toppingEntity.getId() == 1L).findFirst().orElse(mapleSyrup);

    assertThat(found.getName()).isEqualTo(name);
  }

  // These functions below directly comes from jpa repo.
  @Test
  public void crudTest() {
    ToppingEntity mapleSyrup = new ToppingEntity(5L, "Maple syrup", BigDecimal.valueOf(3.0));
    ToppingEntity mapleSyrupBeforeCreation = new ToppingEntity(null, "Maple syrup", BigDecimal.valueOf(3.0));
    ToppingEntity updatedMapleSyrup = new ToppingEntity(5L, "Maple syrup", BigDecimal.valueOf(4.0));

    Mockito.when(toppingRepository.findById(5L)).thenReturn(Optional.of(mapleSyrup));
    Mockito.when(toppingRepository.save(mapleSyrup)).thenReturn(mapleSyrup);

    ToppingInput mapleSyrupInput = new ToppingInput(mapleSyrup.getName(), mapleSyrup.getAmount());
    Mockito.when(toppingRepository
        .save(ToppingEntity.createWithNameAndPrice(mapleSyrup.getName(), mapleSyrup.getAmount())))
        .thenReturn(mapleSyrup);

    Topping savedTopping = toppingService.create(mapleSyrupInput);
    Mockito.verify(toppingRepository, Mockito.times(1)).save(mapleSyrupBeforeCreation);
    assertThat(savedTopping.getName()).isEqualTo(mapleSyrup.getName());

    Mockito.when(toppingRepository.save(updatedMapleSyrup)).thenReturn(updatedMapleSyrup);

    Topping updatedTopping = toppingService.update(Topping.from(updatedMapleSyrup));
    Mockito.verify(toppingRepository, Mockito.times(1)).save(mapleSyrup);
    assertThat(updatedTopping.getAmount()).isEqualTo(updatedMapleSyrup.getAmount());

    mapleSyrup.setId(5L);
    toppingService.delete(mapleSyrup.getId());
    Mockito.verify(toppingRepository, Mockito.times(1)).deleteById(mapleSyrup.getId());
  }
}

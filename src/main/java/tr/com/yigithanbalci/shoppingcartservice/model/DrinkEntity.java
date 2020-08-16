package tr.com.yigithanbalci.shoppingcartservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;

@Data
@Entity
@Table(name = "Drinks")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DrinkEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dringSeqGen")
  @SequenceGenerator(name = "dringSeqGen", sequenceName = "drink_sequence")
  private Long id;

  @Column(nullable = false)
  @NotBlank(message = "Name is not blank")
  private String name;

  @Column(nullable = false)
  @PositiveOrZero(message = "Amount is positive or zero")
  private BigDecimal amount;

  public static DrinkEntity from(Drink drink){
    DrinkEntity drinkEntity = DrinkEntity
        .createWithNameAndPrice(drink.getName(), drink.getAmount());
    drinkEntity.setId(drinkEntity.getId());
    return drinkEntity;
  }

  public static DrinkEntity createWithNameAndPrice(String name, BigDecimal price){
    DrinkEntity drinkEntity = new DrinkEntity();
    drinkEntity.setName(name);
    drinkEntity.setAmount(price);
    return drinkEntity;
  }
}

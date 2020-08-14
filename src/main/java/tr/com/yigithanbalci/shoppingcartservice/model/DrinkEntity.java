package tr.com.yigithanbalci.shoppingcartservice.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class DrinkEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dringSeqGen")
  @SequenceGenerator(name = "dringSeqGen", sequenceName = "drink_sequence")
  private long id;

  @Column
  private String name;

  @Column
  private Float price;

  public static DrinkEntity from(Drink drink){
    DrinkEntity drinkEntity = new DrinkEntity();
    drinkEntity.setName(drink.getName());
    drinkEntity.setPrice(drink.getPrice());
    return drinkEntity;
  }
}

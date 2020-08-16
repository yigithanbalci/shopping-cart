package tr.com.yigithanbalci.shoppingcartservice.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "DrinkToppingRelations")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DrinkToppingRelation implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drinkRelationSeqGen")
  @SequenceGenerator(name = "drinkRelationSeqGen", sequenceName = "drinkRelation_sequence")
  private long id;

  @Column(nullable = false)
  @PositiveOrZero(message = "Drink id is positive or zero")
  private Long drinkId;

  @Column(nullable = false)
  @PositiveOrZero(message = "Topping id is positive or zero")
  private Long toppingId;

  @Column
  @Positive(message = "Drink and Topping usage together is positive")
  private long numberOfUsageTogether = 1;

  public static DrinkToppingRelation createWithDrinkAndTopping(Long drinkId, Long toppingId){
    DrinkToppingRelation drinkToppingRelation = new DrinkToppingRelation();
    drinkToppingRelation.setDrinkId(drinkId);
    drinkToppingRelation.setToppingId(toppingId);
    return drinkToppingRelation;
  }

  public long usedOneMoreTime(){
    return ++(this.numberOfUsageTogether);
  }
}

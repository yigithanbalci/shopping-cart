package tr.com.yigithanbalci.shoppingcartservice.model;

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

@Data
@Builder
@Entity
@Table(name = "DrinkToppingRelations")
@NoArgsConstructor
@AllArgsConstructor
public class DrinkToppingRelation {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drinkRelationSeqGen")
  @SequenceGenerator(name = "drinkRelationSeqGen", sequenceName = "drinkRelation_sequence")
  private Long id;

  @Column
  private Long drinkId;

  @Column
  private Long toppingId;

  @Column
  private Long amount;
}

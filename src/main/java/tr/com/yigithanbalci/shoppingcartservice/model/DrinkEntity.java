package tr.com.yigithanbalci.shoppingcartservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
public class DrinkEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dringSeqGen")
  @SequenceGenerator(name = "dringSeqGen", sequenceName = "drink_sequence")
  private long id;

  @Column
  private String name;

  @Column
  private Float price;
}

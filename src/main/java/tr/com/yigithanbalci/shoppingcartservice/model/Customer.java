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

@Data
@Builder
@Entity
@Table(name = "Customers")
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSeqGen")
  @SequenceGenerator(name = "customerSeqGen", sequenceName = "customer_sequence")
  private long id;

  @Column
  private String username;

  @Column
  private Long userId;

  @Column
  private long totalOrders;
}

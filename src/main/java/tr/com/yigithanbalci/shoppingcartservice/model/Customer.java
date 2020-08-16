package tr.com.yigithanbalci.shoppingcartservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "Customers")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Customer implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSeqGen")
  @SequenceGenerator(name = "customerSeqGen", sequenceName = "customer_sequence")
  private Long id;

  @JsonIgnore
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column
  @PositiveOrZero(message = ORDER_POSITIVE_OR_ZERO)
  private BigDecimal totalAmountOfOrders = BigDecimal.ZERO;

  private transient static final String ORDER_POSITIVE_OR_ZERO = "Total amount of orders are atleast zero";

  public static Customer createWithUserAndTotalOrders(User user,
      @PositiveOrZero(message = ORDER_POSITIVE_OR_ZERO) BigDecimal totalOrders) {
    Customer customer = new Customer();
    customer.setUser(user);
    customer.setTotalAmountOfOrders(totalOrders);
    return customer;
  }

  public BigDecimal orderedNewItemWithAmount(
      @PositiveOrZero(message = ORDER_POSITIVE_OR_ZERO) BigDecimal orderAmount) {
    this.totalAmountOfOrders = this.totalAmountOfOrders.add(orderAmount);
    return this.totalAmountOfOrders;
  }
}

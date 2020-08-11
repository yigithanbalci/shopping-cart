package tr.com.yigithanbalci.shoppingcartservice.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
  @SequenceGenerator(name = "userSeqGen", sequenceName = "user_sequence")
  private Long id;

  @Column
  private String username;

  @Column
  private String password;

  @Column
  private String role;

  @Column
  private boolean enabled;

  @Column
  private Long orders;

  @OneToOne(mappedBy = "user",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL)
  private Customer customer;
}
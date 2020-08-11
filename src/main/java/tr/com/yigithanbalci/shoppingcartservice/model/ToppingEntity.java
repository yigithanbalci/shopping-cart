package tr.com.yigithanbalci.shoppingcartservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class ToppingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toppingSeqGen")
    @SequenceGenerator(name = "toppingSeqGen", sequenceName = "topping_sequence")
    private Long id;

    @Column
    private String name;

    @Column
    private Float price;
}

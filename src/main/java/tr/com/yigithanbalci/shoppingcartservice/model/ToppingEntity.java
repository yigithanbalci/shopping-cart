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
public class ToppingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toppingSeqGen")
    @SequenceGenerator(name = "toppingSeqGen", sequenceName = "topping_sequence")
    private long id;

    @Column
    private String name;

    @Column
    private Float price;
}

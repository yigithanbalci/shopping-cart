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
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;

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
    private long id;

    @Column
    private String name;

    @Column
    private Float price;

    public static ToppingEntity from(Topping topping){
        ToppingEntity toppingEntity = new ToppingEntity();
        toppingEntity.setName(topping.getName());
        toppingEntity.setPrice(topping.getPrice());
        return toppingEntity;
    }
}

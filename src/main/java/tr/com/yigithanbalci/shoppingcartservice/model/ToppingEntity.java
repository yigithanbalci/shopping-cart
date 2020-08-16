package tr.com.yigithanbalci.shoppingcartservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;

@Data
@Entity
@Table(name = "Toppings")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ToppingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toppingSeqGen")
    @SequenceGenerator(name = "toppingSeqGen", sequenceName = "topping_sequence")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is not blank")
    private String name;

    @Column(nullable = false)
    @PositiveOrZero(message = "Amount is positive or zero")
    private BigDecimal amount;

    public static ToppingEntity from(Topping topping){
        return ToppingEntity.createWithNameAndPrice(topping.getName(), topping.getAmount());
    }

    public static ToppingEntity createWithNameAndPrice(String name, BigDecimal price){
        ToppingEntity toppingEntity = new ToppingEntity();
        toppingEntity.setName(name);
        toppingEntity.setAmount(price);
        return toppingEntity;
    }
}

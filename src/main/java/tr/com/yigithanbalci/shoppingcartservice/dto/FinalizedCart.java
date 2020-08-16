package tr.com.yigithanbalci.shoppingcartservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FinalizedCart extends SelfValidating<FinalizedCart> implements Serializable {

  @Positive(message = "Original amount is positive or zero")
  private final BigDecimal originalAmount;

  @Positive(message = "Discounted amount is positive or zero")
  private final BigDecimal discountedAmount;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public static FinalizedCart createWithOriginalAmountAndDiscountedAmount(
      @JsonProperty("originalAmount") BigDecimal originalAmount,
      @JsonProperty("discountedAmount") BigDecimal discountedAmount) {
    FinalizedCart finalizedCart = new FinalizedCart(originalAmount, discountedAmount);
    finalizedCart.validateSelf();
    return finalizedCart;
  }
}
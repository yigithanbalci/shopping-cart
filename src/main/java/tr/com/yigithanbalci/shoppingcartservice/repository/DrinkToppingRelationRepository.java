package tr.com.yigithanbalci.shoppingcartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkToppingRelation;

@Repository
public interface DrinkToppingRelationRepository extends JpaRepository<DrinkToppingRelation, Long> {

  DrinkToppingRelation findTopByDrinkIdEqualsOrderByAmountDesc(Long drinkId);
}

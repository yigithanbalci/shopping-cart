package tr.com.yigithanbalci.shoppingcartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;

@Repository
public interface DrinkRepository extends JpaRepository<DrinkEntity, Long> {

}

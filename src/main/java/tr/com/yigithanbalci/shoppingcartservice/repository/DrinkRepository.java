package tr.com.yigithanbalci.shoppingcartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.yigithanbalci.shoppingcartservice.model.Drink;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

}

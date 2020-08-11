package tr.com.yigithanbalci.shoppingcartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;

@Repository
public interface ToppingRepository extends JpaRepository<ToppingEntity, Long> {

  ToppingEntity findByNameEquals(String name);
}

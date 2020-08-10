package tr.com.yigithanbalci.shoppingcartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.yigithanbalci.shoppingcartservice.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

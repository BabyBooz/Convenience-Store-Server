package demo.shop.Repository;

import demo.shop.Entity.LogoutToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogoutRepository extends JpaRepository<LogoutToken, String> {

}

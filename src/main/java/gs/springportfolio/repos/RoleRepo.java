package gs.springportfolio.repos;

import gs.springportfolio.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepo extends JpaRepository<Role, Long> {

}

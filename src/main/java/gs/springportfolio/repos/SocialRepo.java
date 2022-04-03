package gs.springportfolio.repos;

import gs.springportfolio.models.Social;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialRepo extends JpaRepository<Social, Long> {
}

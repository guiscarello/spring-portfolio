package gs.springportfolio.config.security.user;

import java.util.Optional;

public interface AppUserDetailsDao {

    Optional<AppUserDetails> selectUserDetailsByUsername(String username);

}

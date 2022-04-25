package gs.springportfolio.config.security.user;

import gs.springportfolio.models.User;
import gs.springportfolio.repos.RoleRepo;
import gs.springportfolio.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("jpa")
public class AppUserDetailsJpaService implements AppUserDetailsDao {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Optional<AppUserDetails> selectUserDetailsByUsername(String username) {
        User  user = userRepo.findByUsername(username);

        //log.info("User roles: {}", user.getRoles());
        if (user == null){
            return Optional.empty();
        } else {
            return Optional.of(new AppUserDetails(
                    user,
                    true,
                    true,
                    true,
                    true
            ));
        }

    }
}

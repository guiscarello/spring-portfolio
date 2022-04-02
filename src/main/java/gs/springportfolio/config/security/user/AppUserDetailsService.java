package gs.springportfolio.config.security.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private final AppUserDetailsDao appUserDetailsDao;

    public AppUserDetailsService(@Qualifier("jpa") AppUserDetailsDao appUserDetailsDao) {
        this.appUserDetailsDao = appUserDetailsDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserDetailsDao.selectUserDetailsByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with username %s not found", username)));
    }
}

package gs.springportfolio.services.users;

import gs.springportfolio.models.Role;
import gs.springportfolio.models.User;
import gs.springportfolio.repos.RoleRepo;
import gs.springportfolio.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service @Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User saveUser(String username, String password, String[] roles) {
        Set<Role> userRoles = new HashSet<>();

        for(String role : roles){
            userRoles.add(new Role(
                    role
            ));
        }
        return this.userRepo.save(new User(
                username,
                this.bCryptPasswordEncoder.encode(password),
                userRoles
        ));
    }

    @Override
    public User editUser(Long id, User user) {
        //TODO
        return null;
    }

    @Override
    public Long deleteUser(Long id) throws Exception {
        try{
            this.userRepo.deleteById(id);
            return id;
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Something has gone wrong!", e);
        }
    }
}

package gs.springportfolio.services;

import gs.springportfolio.models.User;
import gs.springportfolio.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User editUser(Long id, User user) {
        return null;
    }

    @Override
    public String deleteUser(Long id) {
        return null;
    }
}

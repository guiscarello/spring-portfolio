package gs.springportfolio.services;

import gs.springportfolio.models.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    User saveUser(User user);
    User editUser(Long id, User user);
    String deleteUser(Long id);

}

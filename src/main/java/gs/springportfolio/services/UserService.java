package gs.springportfolio.services;

import gs.springportfolio.models.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    User saveUser(String username, String password, String[] roles);
    User editUser(Long id, User user);
    String deleteUser(Long id);

}

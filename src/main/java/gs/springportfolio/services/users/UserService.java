package gs.springportfolio.services.users;

import gs.springportfolio.models.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    User saveUser(String username, String password, String[] roles);
    User editUser(Long id, User user);
    Long deleteUser(Long id) throws Exception;

}

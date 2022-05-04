package gs.springportfolio.api;

import gs.springportfolio.models.User;
import gs.springportfolio.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/api")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> saveUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("roles[]") String[] roles
    ){
        return ResponseEntity.ok(this.userService.saveUser(username, password, roles));
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Long> saveUser(
            @PathVariable("id") Long id
    ) throws Exception {
        return ResponseEntity.ok(this.userService.deleteUser(id));
    }

}

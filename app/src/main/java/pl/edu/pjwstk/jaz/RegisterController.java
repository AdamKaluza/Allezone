package pl.edu.pjwstk.jaz;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RegisterController {
    final UserService userService;

    public  HashMap<String, User> users = new HashMap<String, User>();

    public RegisterController(UserService userService) {
        this.userService = userService;

    }

    public HashMap<String, User> getUsersMap() {
        return users;
    }

    public User getUser(String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        }
        return null;
    }


    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest) {

        Set<String> authorities = new HashSet<>();
        authorities.add("user");

        userService.saveUser(registerRequest.getUsername(),registerRequest.getPassword(),authorities);
    }
}



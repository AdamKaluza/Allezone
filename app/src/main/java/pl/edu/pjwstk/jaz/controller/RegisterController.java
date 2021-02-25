package pl.edu.pjwstk.jaz.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.request.RegisterRequest;
import pl.edu.pjwstk.jaz.service.UserService;

import java.util.*;

@RestController
public class RegisterController {
    final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest) {

        Set<String> authorities = new HashSet<>();
        authorities.add("user");

        userService.saveUser(registerRequest.getUsername(),registerRequest.getPassword(),authorities);
    }
}



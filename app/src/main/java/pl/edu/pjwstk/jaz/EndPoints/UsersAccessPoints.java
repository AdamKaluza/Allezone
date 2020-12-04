package pl.edu.pjwstk.jaz.EndPoints;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.LoginRequest;
import pl.edu.pjwstk.jaz.RegisterController;
import pl.edu.pjwstk.jaz.User;

import java.util.HashMap;
import java.util.Set;

@RestController
public class UsersAccessPoints {

    User user;
    RegisterController registerController;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/addAuth")
    public void addAuthorities(@RequestBody LoginRequest loginRequest){
        HashMap<String,User> users = registerController.getUsersMap();
       user = registerController.getUser(loginRequest.getUsername());

      Set<String> authorities = user.getAuthorities();
      authorities.add("view-readiness");

        System.out.println(user.getAuthorities());

    }
}

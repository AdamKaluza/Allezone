package pl.edu.pjwstk.jaz;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class RegisterController {

    public static HashMap<String, User> users = new HashMap<String, User>();

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
        if(users.isEmpty()){
            authorities.add("admin");
        }


        //todo registerUser

        users.put(registerRequest.getUsername(), new User(registerRequest.getUsername(), registerRequest.getPassword(),authorities));

       // System.out.println(users);

    }

}


// todo czy dany uzytkownik istnieje
//        for (Map.Entry<Integer, User> usersEntry : users.entrySet()) {
//            User userMapEntry = usersEntry.getValue();
//            if (userMapEntry.getUsername().equals(registerRequest.getUsername())) {
//                throw new UnauthorizedException();
//            }
//        }
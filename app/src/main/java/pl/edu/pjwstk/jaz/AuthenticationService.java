package pl.edu.pjwstk.jaz;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationService {

    public boolean login(String username, String password) {
        RegisterController registerController = new RegisterController();

        HashMap<Integer, User> users = registerController.getUsersMap();
//        System.out.println(password);
//        System.out.println(users);

        for (Map.Entry<Integer, User> usersEntry : users.entrySet()) {
            User userMapEntry = usersEntry.getValue();
            if (userMapEntry.getUsername().equals(username) && userMapEntry.getPassword().equals(password)) {
                return true;
            }
//            System.out.println("eluwina");
        }
        return false;
    }
}



package pl.edu.pjwstk.jaz;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationService {

    final UserSession userSession;

    public AuthenticationService(UserSession userSession) {
        this.userSession = userSession;
    }

    public boolean login(String username, String password) {

        RegisterController registerController = new RegisterController();

        User checkUser = new User(username, password);
        User registerUser = registerController.getUser(username);

        //  System.out.println(registerUser);

        if (registerUser.getUsername().equals(checkUser.getUsername()) && registerUser.getPassword().equals(checkUser.getPassword())) {
          //  System.out.println(registerUser.getAuthorities());
            userSession.logIn();
            SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(registerUser));
            return true;
        }

        return false;
    }
}

//        HashMap<String, User> users = registerController.getUsersMap();
//        for (Map.Entry<Integer, User> usersEntry : users.entrySet()) {
//            User userMapEntry = usersEntry.getValue();
//            if (userMapEntry.getUsername().equals(username) && userMapEntry.getPassword().equals(password)) {
//                keyValue = usersEntry.getKey();
//                userSession.logIn();
//                SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(users.get(keyValue)));
//                return true;
//            }
//        }
//SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(users.get(keyValue)));
//        try{
//             registerUser =registerController.getUser(username);
//        }catch (UnauthorizedException e){
//            System.out.println("Nie istnieje taki użytkownik");
//        }
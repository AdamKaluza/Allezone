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

        if (registerUser.getUsername().equals(checkUser.getUsername()) && registerUser.getPassword().equals(checkUser.getPassword())) {
            userSession.logIn();
            SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(registerUser));
            return true;
        }

        return false;
    }
}

package pl.edu.pjwstk.jaz;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.jaz.readiness.UserEntity;

@Component
public class AuthenticationService {

    final UserSession userSession;
    final RegisterController registerController;
    final UserService userService;

    public AuthenticationService(UserSession userSession, RegisterController registerController, UserService userService) {
        this.userSession = userSession;
        this.registerController = registerController;
        this.userService = userService;
    }

    public boolean login(String username, String password) {

        UserEntity user = userService.findByUsername(username);

        if (user.getUsername().equals(username) && userService.passwordMatches(password,user.getPassword())) {
            userSession.logIn();
            SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(user));
            return true;
        }

        return false;
    }
}
//      User registerUser = registerController.getUser(username);
//        if (registerUser.getUsername().equals(username) && registerUser.getPassword().equals(password)) {
//            userSession.logIn();
//            SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(registerUser));
//            return true;
//        }
//passwordEncoder.matches(password,user.getPassword())
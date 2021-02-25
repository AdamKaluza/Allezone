package pl.edu.pjwstk.jaz.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.jaz.controller.RegisterController;
import pl.edu.pjwstk.jaz.service.UserService;
import pl.edu.pjwstk.jaz.entity.UserEntity;

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

        if (userService.passwordMatches(password,user.getPassword())) {
            userSession.logIn();
            SecurityContextHolder.getContext().setAuthentication(new AppAuthentication(user));
            return true;
        }

        return false;
    }
}

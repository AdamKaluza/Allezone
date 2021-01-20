package pl.edu.pjwstk.jaz;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final AuthenticationService authenticationService;
    private  String username;



    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
        username = loginRequest.getUsername();

        //zalogowac uwierzytelnic

        var isLogged = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (!isLogged) {
            throw new UnauthorizedException();
        }

    }

    public String getUsername() {
        return username;
    }
}


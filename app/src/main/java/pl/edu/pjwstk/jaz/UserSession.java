package pl.edu.pjwstk.jaz;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS )
@Component
public class UserSession {
    public boolean isLoggedIn() {
        //session id
        return false;
    }
    // tutaj jakaś zmienna informacja
    // która pozwoli określić czy użytkownik jest zalogowany czy nie

    // metody do zarządzania tą informacja
}

package pl.edu.pjwstk.jaz;

import java.util.Collections;
import java.util.Set;

public class User {
    private final String username;
    private final String password;
    private final Set<String> authorities;



    public User(String username, String password, Set<String> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.authorities = Collections.emptySet();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

}

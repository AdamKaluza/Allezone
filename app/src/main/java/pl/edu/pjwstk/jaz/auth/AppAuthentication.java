package pl.edu.pjwstk.jaz.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.edu.pjwstk.jaz.entity.UserEntity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AppAuthentication extends AbstractAuthenticationToken {
    private final UserEntity authenticatedUser;

    public AppAuthentication( UserEntity authenticatedUser) {
        super(toGrantedAuthorities(authenticatedUser.getAuthorities()));
        this.authenticatedUser = authenticatedUser;
        setAuthenticated(true);
    }

    private static Collection<? extends GrantedAuthority> toGrantedAuthorities(Set<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public Object getCredentials() {
        return authenticatedUser.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUser;
    }
}

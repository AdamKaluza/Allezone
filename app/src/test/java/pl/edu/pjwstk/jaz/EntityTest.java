package pl.edu.pjwstk.jaz;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.when;

@RunWith(MockitoJUnitRunner.class)
public class EntityTest {
    @Mock
    EntityManager em;
    @Mock
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void saveUserToDatabase(){
        String username = "admin";
        String password = "password";
        Set<String> authorities = new HashSet<>();
        authorities.add("admin");
        authorities.add("user");

        when();

    }
}

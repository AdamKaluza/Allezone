package pl.edu.pjwstk.jaz.EndPoints;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.Test;

@RestController
public class EndPointAuthorities {

    private final Test test;

    public EndPointAuthorities(Test test) {
        this.test = test;
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/onlyAdmin")
    public void access(){
        System.out.println("Admin zalogowany.");
    }

    @PreAuthorize("hasAnyAuthority('user','admin')")
    @GetMapping("/read")
    public void read(){
        System.out.println("zalogowano");
    }

    @GetMapping("/auth0/filterUse")
    public void filter(){
        System.out.println("Filtr dziala");
    }

    @GetMapping("/auth0/testyy")
    public void testy(){
        test.test();
    }

}

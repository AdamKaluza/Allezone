package pl.edu.pjwstk.jaz;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.readiness.Test1Entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@RestController
public class ReadinessController {
    @PersistenceContext
    private EntityManager entityManager;

    @PreAuthorize("hasAnyAuthority('admin','user')")
    @GetMapping("auth0/is-ready")
    @Transactional
    public void readinessTest(){
        var entity = new Test1Entity();
        entity.setName("sdavsda");
        entityManager.persist(entity);

    }
}

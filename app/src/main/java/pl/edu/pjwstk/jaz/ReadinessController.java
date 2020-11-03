package pl.edu.pjwstk.jaz;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.readiness.Test1Entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class ReadinessController {
    @PersistenceContext
    private EntityManager entityManager;
    @GetMapping("is-ready")
    @Transactional
    public void readinessTest(){
        var entity = new Test1Entity();
        entity.setName("sdavsda");
        entityManager.persist(entity);

    }
}

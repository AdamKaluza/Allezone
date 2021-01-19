package pl.edu.pjwstk.jaz.Request;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.entity.Auction;
import pl.edu.pjwstk.jaz.entity.Category;
import pl.edu.pjwstk.jaz.entity.Section;
import pl.edu.pjwstk.jaz.entity.UserEntity;
import javax.persistence.EntityManager;
import java.util.List;


@Repository
public class AdminService {
    private final EntityManager em;

    public AdminService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void createSection(String sectionName) {
        var section = new Section();
        section.setName(sectionName);
        em.persist(section);
    }

    @Transactional
    public void updateSection(String sectionName,String newName) {
        var section = findSectionByName(sectionName);
        section.setName(newName);
        em.merge(section);
    }


    @Transactional
    public void createCategory(String categoryName,Section section){
        var category  = new Category();

        category.setName(categoryName);
        category.setSection(section);

        em.persist(category);
    }

    public Category findCategoryByName(String categoryName) {
        return em.createQuery("SELECT category FROM Category category WHERE category.name =: categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getSingleResult();
    }


    public List<UserEntity> getUsers(){
        return em.createQuery("select user FROM UserEntity user",UserEntity.class)
                .getResultList();

    }

    public Section findSectionByName(String sectionName) {
        return em.createQuery("SELECT section FROM Section section WHERE section.name =: sectionName", Section.class)
                .setParameter("sectionName", sectionName)
                .getSingleResult();
    }
    public List<Auction> getAuctions(){
        return em.createQuery("select auction FROM Auction auction",Auction.class).getResultList();
    }
    public List<Auction> getAuctionsByCreator(UserEntity userEntity){
        return em.createQuery("select auction FROM Auction auction WHERE auction.userEntity =:userEntity",Auction.class)
                .setParameter("userEntity",userEntity)
                .getResultList();
    }

}

package pl.edu.pjwstk.jaz.Services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.Request.CategoryRequest;
import pl.edu.pjwstk.jaz.entity.Auction;
import pl.edu.pjwstk.jaz.entity.Category;
import pl.edu.pjwstk.jaz.entity.Section;
import pl.edu.pjwstk.jaz.entity.UserEntity;
import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Service
public class SectionService {

    private final EntityManager em;

    public SectionService(EntityManager em) {
        this.em = em;
    }

    public void createSection(String sectionName) {
        var section = new Section();
        section.setName(sectionName);
        em.persist(section);
    }

    public void updateSection(String sectionName,String newName) {
        var section = findSectionByName(sectionName);
        section.setName(newName);
        em.merge(section);
    }

    public void createCategory(CategoryRequest categoryRequest){

        for (String e : categoryRequest.getCategoryName()){
            var category  = new Category();
            category.setName(e);
            category.setSection(findSectionByName(categoryRequest.getSectionName()));
            em.persist(category);
        }
    }

    public void updateCategory(String categoryName,String newName) {
        var category = findCategoryByName(categoryName);
        category.setName(newName);
        em.merge(category);
    }

    public Category findCategoryByName(String categoryName) {
        return em.createQuery("SELECT category FROM Category category WHERE category.name =: categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getSingleResult();
    }


    public Section findSectionByName(String sectionName) {
        return em.createQuery("SELECT section FROM Section section WHERE section.name =: sectionName", Section.class)
                .setParameter("sectionName", sectionName)
                .getSingleResult();
    }

    public List<UserEntity> getUsers(){
        return em.createQuery("select user FROM UserEntity user",UserEntity.class)
                .getResultList();

    }



    public List<Auction> getAuctionsByCreator(UserEntity userEntity){
        return em.createQuery("select auction FROM Auction auction WHERE auction.userEntity =:userEntity",Auction.class)
                .setParameter("userEntity",userEntity)
                .getResultList();
    }

}

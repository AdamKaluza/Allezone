package pl.edu.pjwstk.jaz.Services;

import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.BadRequestException;
import pl.edu.pjwstk.jaz.Request.CategoryRequest;
import pl.edu.pjwstk.jaz.entity.Auction;
import pl.edu.pjwstk.jaz.entity.Category;
import pl.edu.pjwstk.jaz.entity.Section;
import pl.edu.pjwstk.jaz.UserEntity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Transactional
@Service
public class SectionService {

    private final EntityManager em;

    public SectionService(EntityManager em) {
        this.em = em;
    }

    public void createSection(String sectionName) {
        try{
                var section = new Section();
                section.setName(sectionName);
                em.persist(section);

        }catch (NoResultException e){
            throw new BadRequestException();
        }



    }

    public void updateSection(String sectionName,String newName) {
        try{
            var section = findSectionByName(sectionName);
            section.setName(newName);
            em.merge(section);
        }
        catch (NoResultException exception) {
             throw new BadRequestException();
         }
    }

    public void createCategory(CategoryRequest categoryRequest){

        var category  = new Category();
        category.setName(categoryRequest.getCategoryName());
        category.setSection(findSectionByName(categoryRequest.getSectionName()));
        em.persist(category);
    }

    public void updateCategory(String categoryName,CategoryRequest categoryRequest) {
        try{
            var category = findCategoryByName(categoryName);
            category.setName(categoryRequest.getCategoryName());
            if(categoryRequest.getSectionName() != null){
                category.setSection(findSectionByName(categoryRequest.getSectionName()));
            }
            em.merge(category);
        }catch (NoResultException exception) {
            throw new BadRequestException();
        }
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

    public List<Auction> getAuctionsByCreator(UserEntity userEntity){
        return em.createQuery("select auction FROM Auction auction WHERE auction.userEntity =:userEntity",Auction.class)
                .setParameter("userEntity",userEntity)
                .getResultList();
    }

}

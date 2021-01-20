package pl.edu.pjwstk.jaz;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.entity.*;

import javax.persistence.EntityManager;

import java.util.HashSet;
import java.util.Set;

@Repository
public class Test {
    private final EntityManager entityManager;
    private final UserService userService;

    public Test(EntityManager entityManager, UserService userService) {
        this.entityManager = entityManager;
        this.userService = userService;
    }

    @Transactional
    public void test() {

        UserEntity userEntity = userService.findByUsername("admin");

        Section section = new Section();
        section.setName("Elektronika");
        entityManager.persist(section);

        Category category = new Category();
        category.setSection(section);
        category.setName("Monitory");
        entityManager.persist(category);


        Auction auction = new Auction();
        auction.setPrice(123);
        auction.setTitle("Monitor Philips");
        auction.setDescription("Monitor 23 cale full hd");
        auction.setUserEntity(userEntity);
        auction.setCategory(category);
        entityManager.persist(auction);

        Photo photo1 = new Photo();
        photo1.setName("zdjeciee");
        photo1.setPosition(1);
        photo1.setAuction(auction);
        entityManager.persist(photo1);

        Photo photo2 = new Photo();
        photo2.setName("zdjecieeee");
        photo2.setPosition(2);
        photo2.setAuction(auction);
        entityManager.persist(photo2);

        Parameter parameter1 = new Parameter();
        parameter1.setKey("szerokosc");
        entityManager.persist(parameter1);

        Set<Category> categories = new HashSet<>();
        categories.add(category);
        section.setCategory(categories);

        AuctionParameter auctionParameter = new AuctionParameter();
        auctionParameter.setValue("183");
        auctionParameter.setId(new AuctionParameterID(auction.getId(), parameter1.getId()));
        Set<AuctionParameter> auctionParameterSet = new HashSet<>();

        Set<Auction> auctions = new HashSet<>();
        auctions.add(auction);
        category.setAuctions(auctions);
        auctionParameter.setAuction(auction);
        auctionParameter.setParameter(parameter1);
        entityManager.persist(auctionParameter);

    }

}

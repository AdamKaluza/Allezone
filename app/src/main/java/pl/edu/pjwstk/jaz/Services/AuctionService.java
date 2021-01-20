package pl.edu.pjwstk.jaz.Services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.LoginController;
import pl.edu.pjwstk.jaz.Request.AuctionRequest;
import pl.edu.pjwstk.jaz.Request.ParameterRequest;
import pl.edu.pjwstk.jaz.Request.PhotoRequest;
import pl.edu.pjwstk.jaz.entity.*;

import javax.persistence.EntityManager;
import java.util.*;


@Transactional
@Service
public class AuctionService {
    private final LoginController loginController;
    private final EntityManager entityManager;


    public AuctionService(LoginController loginController, EntityManager entityManager) {
        this.loginController = loginController;
        this.entityManager = entityManager;
    }

    public void createAuction(AuctionRequest auctionRequest) {
        var auction = new Auction();

        auction.setTitle(auctionRequest.getAuctionTitle());
        auction.setDescription(auctionRequest.getAuctionDescription());
        auction.setPrice(auctionRequest.getPrice());
        auction.setVersion(1L);
        auction.setUserEntity(findByUsername(loginController.getUsername()));
//        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        auction.setUserEntity(findByUsername(userEntity.getUsername()));
        auction.setCategory(findCategoryByName(auctionRequest.getCategoryName()));
        auction.setAuctionParameters(setAuctionParameters(auctionRequest.getParameterRequestList(), auction));
        auction.setPhotosSet(setAuctionPhotos(auctionRequest.getPhotoRequestList(),auction));
//        setAuctionPhotos(auctionRequest.getPhotoRequestList(),auction);
//        setAuctionParameters(auctionRequest.getParameterRequestList(),auction);
        entityManager.persist(auction);

    }

    public void editAuction(AuctionRequest auctionRequest,Long auction_id){

        var auction = findAuctionById(auction_id);

        if (!auctionRequest.getAuctionTitle().isEmpty()){
            auction.setTitle(auctionRequest.getAuctionTitle());
        }
        if(!auctionRequest.getAuctionDescription().isEmpty()){
            auction.setDescription(auctionRequest.getAuctionDescription());
        }
        if (!String.valueOf(auctionRequest.getPrice()).isEmpty()){
            auction.setPrice(auctionRequest.getPrice());
        }
        if (!auctionRequest.getCategoryName().isEmpty()){
            auction.setCategory(findCategoryByName(auctionRequest.getCategoryName()));
        }
        if (!auctionRequest.getPhotoRequestList().isEmpty()){
            auction.setPhotosSet(setAuctionPhotos(auctionRequest.getPhotoRequestList(),auction));
        }
        if(!auctionRequest.getParameterRequestList().isEmpty()){
            auction.setPhotosSet(setAuctionPhotos(auctionRequest.getPhotoRequestList(),auction));
        }
        auction.setVersion(auction.getVersion()+1);
        entityManager.merge(auction);

    }

    public Set<AuctionParameter> setAuctionParameters(List<ParameterRequest> parameters, Auction auction) {

        Set<AuctionParameter> auctionParameters = new HashSet<>();

        for (ParameterRequest parameterRequest : parameters) {
            var parameter = new Parameter();
            parameter.setKey(parameterRequest.getParameterKey());
            //  entityManager.persist(parameter);

            var auctionParameter = new AuctionParameter();

            // AuctionParameterID id = new AuctionParameterID(auction.getId(),parameter.getId());

            //    auctionParameter.setId(id);
            auctionParameter.setAuction(auction);
            auctionParameter.setParameter(parameter);
            auctionParameter.setValue(parameterRequest.getParameterValue());
            auctionParameters.add(auctionParameter);

            //    entityManager.persist(auctionParameter);

        }
        return auctionParameters;
    }

    public Set<Photo> setAuctionPhotos(List<PhotoRequest> auctionPhotos, Auction auction) {

        Set<Photo> auctionPhotosSet = new HashSet<>();

        for (PhotoRequest photo : auctionPhotos) {
            var auctionPhoto = new Photo();
            auctionPhoto.setName(photo.getPhotoName());
            auctionPhoto.setPosition(photo.getPhotoPosition());
            auctionPhoto.setAuction(auction);
            auctionPhotosSet.add(auctionPhoto);
            // entityManager.persist(auctionPhoto);
        }
        return auctionPhotosSet;

    }

    public Category findCategoryByName(String categoryName) {
        return entityManager.createQuery("SELECT category FROM Category category WHERE category.name =: categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getSingleResult();
    }

    public UserEntity findByUsername(String username) {
        return entityManager.createQuery("SELECT user FROM UserEntity user WHERE user.username =: username", UserEntity.class)
                .setParameter("username", username) //username!!!
                .getSingleResult();
    }

    public Auction findAuctionById(Long auction_id) {
        return entityManager.createQuery("SELECT auction FROM Auction auction WHERE auction.id =: auction_id", Auction.class)
                .setParameter("auction_id", auction_id)
                .getSingleResult();
    }
}


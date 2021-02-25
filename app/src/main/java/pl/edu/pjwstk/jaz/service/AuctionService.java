package pl.edu.pjwstk.jaz.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.exception.BadRequestException;
import pl.edu.pjwstk.jaz.exception.UnauthorizedException;
import pl.edu.pjwstk.jaz.request.AuctionRequest;
import pl.edu.pjwstk.jaz.request.ParameterRequest;
import pl.edu.pjwstk.jaz.request.PhotoRequest;
import pl.edu.pjwstk.jaz.entity.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;


@Transactional
@Service
public class AuctionService {


    private final EntityManager entityManager;


    public AuctionService( EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createAuction(AuctionRequest auctionRequest) {
        var auction = new Auction();
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            auction.setTitle(auctionRequest.getAuctionTitle());
            auction.setDescription(auctionRequest.getAuctionDescription());
            auction.setPrice(auctionRequest.getPrice());
            auction.setVersion(1L);
            auction.setUserEntity(userEntity);
            auction.setCategory(findCategoryByName(auctionRequest.getCategoryName()));
            auction.setAuctionParameters(setAuctionParameters(auctionRequest.getParameterRequestList(), auction));
            auction.setPhotosSet(setAuctionPhotos(auctionRequest.getPhotoRequestList(), auction));
            entityManager.persist(auction);
    }

    public void editAuction(AuctionRequest auctionRequest, Long auction_id) {

        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var auction = findAuctionById(auction_id);

        if (userEntity.getUsername().equals(auction.getUserEntity().getUsername())) {

            if (auctionRequest.getAuctionTitle() != null) {
                auction.setTitle(auctionRequest.getAuctionTitle());
            }
            if (auctionRequest.getAuctionDescription() != null) {
                auction.setDescription(auctionRequest.getAuctionDescription());
            }
            if (auctionRequest.getPrice() != 0) {
                auction.setPrice(auctionRequest.getPrice());
            }
            if (auctionRequest.getCategoryName() != null) {
                try {
                    if (findCategoryByName(auctionRequest.getCategoryName()) != null) {
                        auction.setCategory(findCategoryByName(auctionRequest.getCategoryName()));
                    }
                } catch (NoResultException exception) {
                    throw new BadRequestException();
                }
            }
            if (auctionRequest.getPhotoRequestList() != null) {
                for (Photo p : auction.getPhotosSet()){
                    deletePhoto(p);
                }
                for (Photo p : setAuctionPhotos(auctionRequest.getPhotoRequestList(), auction)){
                    entityManager.persist(p);
                }
            }
            if (auctionRequest.getParameterRequestList() != null) {
                for(Parameter auctionParameter : getAllParametersForAuction(auction_id)){
                    deleteParameter(auctionParameter);
                }
                auction.setAuctionParameters(setAuctionParameters(auctionRequest.getParameterRequestList(), auction));
            }
            if (auctionRequest.getVersion().equals(auction.getVersion())) {
                auction.setVersion(auction.getVersion() + 1);
                entityManager.merge(auction);
            } else {
                throw new BadRequestException();
            }

        } else {
            throw new UnauthorizedException();
        }
    }

    public Set<AuctionParameter> setAuctionParameters(List<ParameterRequest> parameters, Auction auction) {

        Set<AuctionParameter> auctionParameters = new HashSet<>();

        for (ParameterRequest parameterRequest : parameters) {
            var parameter = new Parameter();
            parameter.setKey(parameterRequest.getParameterKey());

            var auctionParameter = new AuctionParameter();
            auctionParameter.setAuction(auction);
            auctionParameter.setParameter(parameter);
            auctionParameter.setValue(parameterRequest.getParameterValue());
            auctionParameters.add(auctionParameter);
        }
        return auctionParameters;
    }

    public Set<Photo> setAuctionPhotos(List<PhotoRequest> auctionPhotos, Auction auction) {

        Set<Photo> auctionPhotosSet = new HashSet<>();

        for (PhotoRequest photo : auctionPhotos) {
            var auctionPhoto = new Photo();
            auctionPhoto.setAuction(auction);
            auctionPhoto.setName(photo.getPhotoName());
            auctionPhoto.setPosition(photo.getPhotoPosition());
            auctionPhotosSet.add(auctionPhoto);
        }
        return auctionPhotosSet;

    }

    public Category findCategoryByName(String categoryName) {
        return entityManager.createQuery("SELECT category FROM Category category WHERE category.name =: categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getSingleResult();
    }

    public UserEntity findUserByUsername(String username) {
        return entityManager.createQuery("SELECT user FROM UserEntity user WHERE user.username =: username", UserEntity.class)
                .setParameter("username", username) //username!!!
                .getSingleResult();
    }

    public Auction findAuctionById(Long auction_id) {
        return entityManager.createQuery("SELECT auction FROM Auction auction WHERE auction.id =: auction_id", Auction.class)
                .setParameter("auction_id", auction_id)
                .getSingleResult();
    }
    public void deleteParameter(Parameter parameter){
        entityManager.createQuery("DELETE FROM AuctionParameter auctionparameter  where auctionparameter.parameter =:parameter")
                .setParameter("parameter",parameter)
                .executeUpdate();
        entityManager.createQuery("DELETE FROM Parameter parameter where parameter=:parameter")
                .setParameter("parameter",parameter)
                .executeUpdate();
    }
    public void deletePhoto(Photo photo){
        entityManager.createQuery("DELETE FROM Photo photo where photo=:photo")
                .setParameter("photo",photo)
                .executeUpdate();
    }

    public List<Parameter> getAllParametersForAuction(Long auction){
        return entityManager.createQuery("SELECT auctionparameter.parameter FROM AuctionParameter auctionparameter WHERE " +
                "auctionparameter.auction.id =: auction",Parameter.class)
                .setParameter("auction",auction)
                .getResultList();
    }
}



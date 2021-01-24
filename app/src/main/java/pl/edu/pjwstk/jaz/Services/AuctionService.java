package pl.edu.pjwstk.jaz.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.BadRequestException;
import pl.edu.pjwstk.jaz.LoginController;
import pl.edu.pjwstk.jaz.Request.AuctionRequest;
import pl.edu.pjwstk.jaz.Request.ParameterRequest;
import pl.edu.pjwstk.jaz.Request.PhotoRequest;
import pl.edu.pjwstk.jaz.UnauthorizedException;
import pl.edu.pjwstk.jaz.UserEntity;
import pl.edu.pjwstk.jaz.entity.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        auction.setUserEntity(findUserByUsername(loginController.getUsername()));
        auction.setCategory(findCategoryByName(auctionRequest.getCategoryName()));
        auction.setAuctionParameters(setAuctionParameters(auctionRequest.getParameterRequestList(), auction));
        auction.setPhotosSet(setAuctionPhotos(auctionRequest.getPhotoRequestList(), auction));
        entityManager.persist(auction);

    }

//    public void returnAuction(AuctionRequest auctionRequest,Long auction_id){
//        entityManager.merge(editAuction(auctionRequest,auction_id));
//    }
//
//    public Auction editAuction(AuctionRequest auctionRequest, Long auction_id) {
//
//        var auction = findAuctionById(auction_id);
//
//        if (findUserByUsername(loginController.getUsername()).equals(auction.getUserEntity())) {
//
//            if (auctionRequest.getAuctionTitle() != null) {
//                auction.setTitle(auctionRequest.getAuctionTitle());
//            }
//            if (auctionRequest.getAuctionDescription() != null) {
//                auction.setDescription(auctionRequest.getAuctionDescription());
//            }
//            if (auctionRequest.getPrice() != 0) {
//                auction.setPrice(auctionRequest.getPrice());
//            }
//            if (auctionRequest.getCategoryName() != null) {
//                try {
//                    if (findCategoryByName(auctionRequest.getCategoryName()) != null) {
//                        auction.setCategory(findCategoryByName(auctionRequest.getCategoryName()));
//                    }
//                } catch (NoResultException exception) {
//                    throw new BadRequestException();
//                }
//            }
//            if (auctionRequest.getPhotoRequestList() != null) {
//                auction.setPhotosSet(setAuctionPhotos(auctionRequest.getPhotoRequestList(), auction));
//            }
//            if (auctionRequest.getParameterRequestList() != null) {
//                for(Parameter auctionParameter : getAllParametersForAuction(auction_id)){
//                    System.out.println(auctionParameter);
//                    entityManager.remove(auctionParameter);
//                }
//
//                auction.setAuctionParameters(setAuctionParameters(auctionRequest.getParameterRequestList(), auction));
//            }
//            if (auctionRequest.getVersion().equals(auction.getVersion())) {
//                auction.setVersion(auction.getVersion() + 1);
//
//            } else {
//                throw new BadRequestException();
//            }
//
//        } else {
//
//            throw new UnauthorizedException();
//        }
//        return auction;
//    }

    public void editAuction(AuctionRequest auctionRequest, Long auction_id) {

        var auction = findAuctionById(auction_id);

        if (findUserByUsername(loginController.getUsername()).equals(auction.getUserEntity())) {

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
               // auction.setPhotosSet(setAuctionPhotos(auctionRequest.getPhotoRequestList(), auction));
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
        System.out.println(auction);

        Set<Photo> auctionPhotosSet = new HashSet<>();

        for (PhotoRequest photo : auctionPhotos) {
            var auctionPhoto = new Photo();
            auctionPhoto.setAuction(auction);
            System.out.println(auction);
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



    public String getMiniature(Long auction_id,int positionPhoto){
        return (String) entityManager.createQuery("SELECT photo.name FROM Photo photo WHERE photo.auction.id =: auction_id AND photo.position =: positionPhoto")
                .setParameter("positionPhoto",positionPhoto)
                .setParameter("auction_id", auction_id)
                .getSingleResult();
    }
    public List<Parameter> getAllParametersForAuction(Long auction){
        return entityManager.createQuery("SELECT auctionparameter.parameter FROM AuctionParameter auctionparameter WHERE " +
                "auctionparameter.auction.id =: auction",Parameter.class)
                .setParameter("auction",auction)
                .getResultList();
    }
}


//        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        auction.setUserEntity(findByUsername(userEntity.getUsername()));

//    public AuctionView getAuction(Long auctionId){
//
//        AuctionView auctionView = new AuctionView();
//
//        try{
//            var auction = findAuctionById(auctionId);
//
//            auctionView.setAuctionId(auctionId);
//            auctionView.setPrice(auction.getPrice());
//            auctionView.setAuctionTitle(auction.getTitle());
//            auctionView.setAuctionDescription(auction.getDescription());
//            auctionView.setCategoryName(auction.getCategory().getName());
//            auctionView.setUserName(auction.getUserEntity().getUsername());
//
//
//            Set<PhotoRequest> photoRequests = new HashSet<>();
//            for (Photo photo : auction.getPhotosSet()){
//                var auctionPhoto = new PhotoRequest(photo.getName(), photo.getPosition());
//                photoRequests.add(auctionPhoto);
//            }
//            auctionView.setPhotoList(photoRequests);
//
//            Set<ParameterRequest> auctionParameters = new HashSet<>();
//            for (AuctionParameter auctionParameter : auction.getAuctionParameters()){
//                var parameter = new ParameterRequest(auctionParameter.getParameter().getKey(),auctionParameter.getValue());
//                auctionParameters.add(parameter);
//            }
//            auctionView.setParameterList(auctionParameters);
//
//            auctionView.setVersion(auction.getVersion());
//        }catch (NoResultException exception) {
//            throw new BadRequestException();
//        }
//
//        return auctionView;
//    }

//    public List<AuctionView> getAuctionList(){
//        List<Auction> auctions = getAuctions();
//        List<AuctionView> auctionViews = new ArrayList<>();
//        for (Auction auction : auctions){
//            var auctionView = getAuction(auction.getId());
//            auctionViews.add(auctionView);
//        }
//
//        return auctionViews;
//    }
//public List<AuctionListView> getAuctionList(){
//        List<Auction> auctions = getAllAuctions();
//        List<AuctionListView> auctionViews = new ArrayList<>();
//        for (Auction auction : auctions){
//            var auctionView = new AuctionListView(auction.getPrice(),auction.getTitle()
//                    ,getMiniature(auction.getId(),1));
//            auctionViews.add(auctionView);
//
//        }
//
//        return auctionViews;
//    }


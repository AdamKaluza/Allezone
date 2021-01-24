package pl.edu.pjwstk.jaz.Services;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.jaz.BadRequestException;
import pl.edu.pjwstk.jaz.Request.ParameterRequest;
import pl.edu.pjwstk.jaz.Request.PhotoRequest;
import pl.edu.pjwstk.jaz.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AuctionViewService {

    private final EntityManager entityManager;

    public AuctionViewService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AuctionViewBefore getAuction(Long auctionId){

        AuctionViewBefore auctionViewBefore = new AuctionViewBefore();

        try{
            var auction = findAuctionById(auctionId);

            auctionViewBefore.setAuctionId(auctionId);
            auctionViewBefore.setPrice(auction.getPrice());
            auctionViewBefore.setAuctionTitle(auction.getTitle());
            auctionViewBefore.setAuctionDescription(auction.getDescription());
            auctionViewBefore.setCategoryName(auction.getCategory().getName());
            auctionViewBefore.setUserName(auction.getUserEntity().getUsername());


            Set<PhotoRequest> photoRequests = new HashSet<>();
            for (Photo photo : auction.getPhotosSet()){
                var auctionPhoto = new PhotoRequest(photo.getName(), photo.getPosition());
                photoRequests.add(auctionPhoto);
            }
            auctionViewBefore.setPhotoList(photoRequests);

            Set<ParameterRequest> auctionParameters = new HashSet<>();
            for (AuctionParameter auctionParameter : auction.getAuctionParameters()){
                var parameter = new ParameterRequest(auctionParameter.getParameter().getKey(),auctionParameter.getValue());
                auctionParameters.add(parameter);
            }
            auctionViewBefore.setParameterList(auctionParameters);

            auctionViewBefore.setVersion(auction.getVersion());
        }catch (NoResultException exception) {
            throw new BadRequestException();
        }

        return auctionViewBefore;
    }


    public List<View> getAllAuctions(){
        return getListAuctionViews();
    }


    public List<View> getListAuctionViews(){
        return entityManager.createQuery("select view FROM View view",View.class)
                .getResultList();
    }

    public Auction findAuctionById(Long auction_id) {
        return entityManager.createQuery("SELECT auction FROM Auction auction WHERE auction.id =: auction_id", Auction.class)
                .setParameter("auction_id", auction_id)
                .getSingleResult();
    }


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
//    public List<AuctionListView> getAuctionList(){
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
}

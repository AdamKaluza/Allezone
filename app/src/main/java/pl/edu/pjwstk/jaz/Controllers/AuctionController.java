package pl.edu.pjwstk.jaz.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.Request.AuctionRequest;
import pl.edu.pjwstk.jaz.Services.AuctionService;
import pl.edu.pjwstk.jaz.entity.AuctionListView;
import pl.edu.pjwstk.jaz.entity.AuctionView;

import java.util.List;

@RestController
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/addAuction")
    public void creteAuction(@RequestBody AuctionRequest auctionRequest){
        auctionService.createAuction(auctionRequest);
    }

    @PreAuthorize("hasAuthority('user')")
    @PutMapping("/editAuction/{auction_id}")
    public void editAuction(@RequestBody AuctionRequest auctionRequest, @PathVariable Long auction_id){
        auctionService.editAuction(auctionRequest,auction_id);
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/auctions/{auctionId}")
    public AuctionView getAuction(@PathVariable Long auctionId){
        return auctionService.getAuction(auctionId);
    }

    @GetMapping("/auctions")
    public List<AuctionListView> getAllAuctions(){
        return auctionService.getAuctionList();
    }

}

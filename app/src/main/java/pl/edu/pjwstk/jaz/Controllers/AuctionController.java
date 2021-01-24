package pl.edu.pjwstk.jaz.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.Request.AuctionRequest;
import pl.edu.pjwstk.jaz.Services.AuctionService;
import pl.edu.pjwstk.jaz.Services.AuctionViewService;
import pl.edu.pjwstk.jaz.entity.AuctionViewBefore;
import pl.edu.pjwstk.jaz.entity.View;

import java.util.List;

@RestController
public class AuctionController {

    private final AuctionService auctionService;
    private final AuctionViewService auctionView;

    public AuctionController(AuctionService auctionService, AuctionViewService auctionView) {
        this.auctionService = auctionService;
        this.auctionView = auctionView;
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
    public AuctionViewBefore getAuction(@PathVariable Long auctionId){
        return auctionView.getAuction(auctionId);
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/auctions")
    public List<View> getAllAuctions(){
        return auctionView.getAllAuctions();
    }

}

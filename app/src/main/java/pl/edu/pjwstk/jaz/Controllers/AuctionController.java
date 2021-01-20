package pl.edu.pjwstk.jaz.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.Request.AuctionRequest;
import pl.edu.pjwstk.jaz.Services.AuctionService;

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
    public void editAuction(@RequestBody AuctionRequest auctionRequest, @PathVariable(name = "auction_id") Long auction_id){
        auctionService.editAuction(auctionRequest,auction_id);
    }

}

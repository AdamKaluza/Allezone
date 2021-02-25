package pl.edu.pjwstk.jaz.service;

import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.entity.Auction;

import java.util.List;

@Repository
public interface QueriesInterface {

    List<Auction> gtAllAuctions();
    Auction findAuctionById(Long auctionId);
}

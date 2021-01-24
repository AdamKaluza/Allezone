package pl.edu.pjwstk.jaz.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "auction_view")
@Immutable
public class View implements Serializable {

    @Id
    @Column(name = "auction_id")
    private Long auction_id;
    @Column(name = "auctiontitle" )
    private String auctionTitle;
    @Column(name = "price" )
    private int productPrice;
    @Column(name = "miniature")
    private String photoMiniature;

    public Long getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(Long auction_id) {
        this.auction_id = auction_id;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public void setAuctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getPhotoMiniature() {
        return photoMiniature;
    }

    public void setPhotoMiniature(String photoMiniature) {
        this.photoMiniature = photoMiniature;
    }
}

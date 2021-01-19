package pl.edu.pjwstk.jaz.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "auction")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;

    private String title;

    private String description;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany
    @JoinColumn(name = "auction_id")
    private Set<Photos> photosSet;

    @OneToMany(mappedBy = "auction")
    private Set<AuctionParameter> auctionParameters;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Set<Photos> getPhotosSet() {
        return photosSet;
    }

    public void setPhotosSet(Set<Photos> photosSet) {
        this.photosSet = photosSet;
    }

    public Set<AuctionParameter> getAuctionParameters() {
        return auctionParameters;
    }

    public void setAuctionParameters(Set<AuctionParameter> auctionParameters) {
        this.auctionParameters = auctionParameters;
    }
}

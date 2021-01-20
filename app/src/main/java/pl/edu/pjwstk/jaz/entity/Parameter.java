package pl.edu.pjwstk.jaz.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parameter")
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String key;

    @OneToMany(mappedBy = "parameter",orphanRemoval = true,cascade = CascadeType.ALL)
    private Set<AuctionParameter> auctionParameters;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<AuctionParameter> getAuctionParameters() {
        return auctionParameters;
    }

    public void setAuctionParameters(Set<AuctionParameter> auctionParameters) {
        this.auctionParameters = auctionParameters;
    }
}

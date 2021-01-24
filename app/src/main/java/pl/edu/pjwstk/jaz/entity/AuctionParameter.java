package pl.edu.pjwstk.jaz.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "auction_parameter")
public class AuctionParameter implements Serializable  {

    @EmbeddedId
    private AuctionParameterID id = new AuctionParameterID();

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("auction_id")
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("parameter_id")
    @JoinColumn(name = "parameter_id")
    private Parameter parameter;

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AuctionParameterID getId() {
        return id;
    }

    public void setId(AuctionParameterID id) {
        this.id = id;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
    
}


package pl.edu.pjwstk.jaz.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "auction_parameter")
public class AuctionParameter implements Serializable  {

    @EmbeddedId
    private AuctionParameterID id;

    @ManyToOne
    @MapsId("auction_id")
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
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


//@Embeddable
//class AuctionParameterID implements Serializable {
//
//    @Column(name = "auction_id")
//    Long auction_id;
//    @Column(name = "parameter_id")
//    Long parameter_id;
//
//    public AuctionParameterID(Long auction_id, Long parameter_id) {
//        this.auction_id = auction_id;
//        this.parameter_id = parameter_id;
//    }
//
//    public AuctionParameterID() {
//    }
//
//    public AuctionParameterID(Long auction_id) {
//        this.auction_id = auction_id;
//    }
//
//    public Long getAuction_id() {
//        return auction_id;
//    }
//
//    public void setAuction_id(Long auction_id) {
//        this.auction_id = auction_id;
//    }
//
//    public Long getParameter_id() {
//        return parameter_id;
//    }
//
//    public void setParameter_id(Long parameter_id) {
//        this.parameter_id = parameter_id;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof AuctionParameterID)) return false;
//        AuctionParameterID that = (AuctionParameterID) o;
//        return Objects.equals(getAuction_id(), that.getAuction_id()) && Objects.equals(getParameter_id(), that.getParameter_id());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getAuction_id(), getParameter_id());
//    }
//}

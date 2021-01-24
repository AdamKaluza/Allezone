package pl.edu.pjwstk.jaz.Request;

import com.sun.istack.NotNull;
import org.springframework.lang.NonNull;

import java.util.List;

public class AuctionRequest {

    private int price;
    private String auctionTitle;
    private String auctionDescription;
    private String categoryName;
    private String userName;
    private Long version;
    private List<PhotoRequest> photoRequestList;
    private List<ParameterRequest> parameterRequestList;

    public AuctionRequest() {
    }

    public AuctionRequest(int price, String auctionTitle, String auctionDescription, String categoryName, String userName, Long version, List<PhotoRequest> photoRequestList, List<ParameterRequest> parameterRequestList) {
        this.price = price;
        this.auctionTitle = auctionTitle;
        this.auctionDescription = auctionDescription;
        this.categoryName = categoryName;
        this.userName = userName;
        this.version = version;
        this.photoRequestList = photoRequestList;
        this.parameterRequestList = parameterRequestList;
    }

    public AuctionRequest(int price, String auctionTitle, String auctionDescription, String categoryName, List<PhotoRequest> photoRequestList, List<ParameterRequest> parameterRequestList) {
        this.price = price;
        this.auctionTitle = auctionTitle;
        this.auctionDescription = auctionDescription;
        this.categoryName = categoryName;
        this.photoRequestList = photoRequestList;
        this.parameterRequestList = parameterRequestList;
    }

    public int getPrice() {
        return price;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public String getAuctionDescription() {
        return auctionDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getUserName() {
        return userName;
    }

    public Long getVersion() {
        return version;
    }

    public List<PhotoRequest> getPhotoRequestList() {
        return photoRequestList;
    }

    public List<ParameterRequest> getParameterRequestList() {
        return parameterRequestList;
    }
}

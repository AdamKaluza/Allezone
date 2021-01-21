package pl.edu.pjwstk.jaz.entity;

public class AuctionListView {

    private int price;
    private String title;
    private String photoMiniature;

    public AuctionListView(int price, String title, String photoMiniature) {
        this.price = price;
        this.title = title;
        this.photoMiniature = photoMiniature;
    }

    public AuctionListView() {
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

    public String getPhotoMiniature() {
        return photoMiniature;
    }

    public void setPhotoMiniature(String photoMiniature) {
        this.photoMiniature = photoMiniature;
    }
}

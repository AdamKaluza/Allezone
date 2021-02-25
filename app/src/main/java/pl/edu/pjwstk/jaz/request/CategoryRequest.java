package pl.edu.pjwstk.jaz.request;

public class CategoryRequest {

    private String categoryName;
    private String sectionName;

    public CategoryRequest(String categoryName, String sectionName) {
        this.categoryName = categoryName;
        this.sectionName = sectionName;
    }

    public CategoryRequest() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getSectionName() {
        return sectionName;
    }

}


package pl.edu.pjwstk.jaz.Request;

public class CategoryRequest {

    private String categoryName;
    private String sectionName;

    public CategoryRequest(String categoryName, String sectionName) {
        this.categoryName = categoryName;
        this.sectionName = sectionName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}

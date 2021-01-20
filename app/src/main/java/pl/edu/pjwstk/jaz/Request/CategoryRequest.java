package pl.edu.pjwstk.jaz.Request;

import java.util.List;

public class CategoryRequest {

    private List<String> categoryName;
    private String sectionName;

    public CategoryRequest(List<String> categoryName, String sectionName) {
        this.categoryName = categoryName;
        this.sectionName = sectionName;
    }

    public CategoryRequest() {
    }

    public List<String> getCategoryName() {
        return categoryName;
    }

    public String getSectionName() {
        return sectionName;
    }

}


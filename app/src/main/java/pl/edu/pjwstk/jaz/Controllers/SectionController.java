package pl.edu.pjwstk.jaz.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.Request.CategoryRequest;
import pl.edu.pjwstk.jaz.Services.SectionService;
import pl.edu.pjwstk.jaz.Request.SectionRequest;

@RestController
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }


    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/addSection")
    public void addSection(@RequestBody SectionRequest sectionRequest){
        sectionService.createSection(sectionRequest.getName());
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/updateSection/{sectionName}")
    public void updateSection(@RequestBody SectionRequest sectionRequest, @PathVariable(name = "sectionName") String sectionName){
        sectionService.updateSection(sectionName,sectionRequest.getName());
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/addCategory")
    public void createCategory(@RequestBody CategoryRequest categoryRequest){
        sectionService.createCategory(categoryRequest);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/updateCategory/{categoryName}")
    public void updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable String categoryName){
        sectionService.updateCategory(categoryName,categoryRequest);
    }

}

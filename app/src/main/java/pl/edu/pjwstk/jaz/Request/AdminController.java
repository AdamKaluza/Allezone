package pl.edu.pjwstk.jaz.Request;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/addSection")
    public void addSection(@RequestBody SectionRequest sectionRequest){
        adminService.createSection(sectionRequest.getName());
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/updateSection/{sectionName}")
    public void updateSection(@RequestBody SectionRequest sectionRequest, @PathVariable String sectionName){
        adminService.updateSection(sectionRequest.getName(),sectionName);
    }

}

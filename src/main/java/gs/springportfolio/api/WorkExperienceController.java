package gs.springportfolio.api;

import gs.springportfolio.dto.WorkExperienceDTO;
import gs.springportfolio.models.WorkExperience;
import gs.springportfolio.services.PhotoFileManagerServiceImpl;
import gs.springportfolio.services.WorkExperienceServiceImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Getter @Setter
@RequestMapping(path = "/api")
@RestController
public class WorkExperienceController {

    private final WorkExperienceServiceImpl workExperienceServiceImpl;
    private final PhotoFileManagerServiceImpl photoFileManagerService;

    @Value("${application.work-experience.photos.upload.folder}")
    private String uploadFolder;

    @Autowired
    public WorkExperienceController(WorkExperienceServiceImpl workExperienceServiceImpl, PhotoFileManagerServiceImpl photoFileManagerService) {
        this.workExperienceServiceImpl = workExperienceServiceImpl;
        this.photoFileManagerService = photoFileManagerService;
    }

    @GetMapping(path = "/work-experiences")
    @ResponseBody
    public List<WorkExperience> getWorkExperiences(){
        return this.workExperienceServiceImpl.getAllWorkExperiences();
    }

    @PostMapping(path = "/work-experiences",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<WorkExperience> addWorkExperience(@RequestBody
            @RequestParam("companyName") String companyName,
            @RequestParam("companyLogo") MultipartFile companyLogo,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("position") String position,
            @RequestParam("description") String description,
            @RequestParam("tel") String  tel,
            @RequestParam("currentWork") boolean currentWork
    ) {
        String  companyLogoPath = photoFileManagerService.uploadFile(companyLogo, uploadFolder);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        WorkExperience newWorkExperience =  workExperienceServiceImpl.addNewWorkExperience(
            new WorkExperience(
                    companyLogoPath,
                    companyName,
                    LocalDate.parse(startDate, formatter),
                    LocalDate.parse(endDate, formatter),
                    position,
                    description,
                    tel,
                    currentWork
            )
        );
        return ResponseEntity.ok(newWorkExperience);
    }

    @PutMapping(path = "/work-experiences/{id}")
    public ResponseEntity<WorkExperience> editWorkExperience(
            @PathVariable Long id,
            @RequestParam("companyName") String companyName,
            @RequestParam(value = "companyLogo", required = false) MultipartFile companyLogo,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("position") String position,
            @RequestParam("description") String description,
            @RequestParam("tel") String  tel,
            @RequestParam("currentWork") boolean isCurrentWork

    ){
        WorkExperienceDTO workExperienceDTO = new WorkExperienceDTO();
        if(companyLogo != null) {
            String companyLogoPath = photoFileManagerService.uploadFile(companyLogo, uploadFolder);
            workExperienceDTO.setCompanyLogoPath(companyLogoPath);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        workExperienceDTO.setCompanyName(companyName);
        workExperienceDTO.setCurrentWork(isCurrentWork);
        workExperienceDTO.setDescription(description);
        workExperienceDTO.setStartDate(LocalDate.parse(startDate, formatter));
        workExperienceDTO.setEndDate(LocalDate.parse(endDate, formatter));
        workExperienceDTO.setPosition(position);
        workExperienceDTO.setDescription(description);
        workExperienceDTO.setTel(tel);

        WorkExperience updatedWorkExperience = this.workExperienceServiceImpl.editWorkExperience(id, workExperienceDTO);

        return ResponseEntity.ok(updatedWorkExperience);
    }

    @DeleteMapping(path = "/work-experiences/{id}")
    public ResponseEntity<Long> deleteWorkExperience(@PathVariable Long id) throws Exception {
        Long deletedWorkExperienceId= this.workExperienceServiceImpl.deleteWorkExperience(id);
        return ResponseEntity.ok(deletedWorkExperienceId);
    }

}

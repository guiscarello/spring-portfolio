package gs.springportfolio.api;

import gs.springportfolio.dto.EducationDTO;
import gs.springportfolio.models.Education;
import gs.springportfolio.services.educations.FirebaseEducationServiceImpl;
import gs.springportfolio.services.files.FirebaseImageFileManagerService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Getter
@Setter
@RequestMapping(path = "/api")
@RestController
public class EducationController {

    //private final LocalEducationServiceImpl localEducationServiceImpl;
   // private final LocalImageFileManagerServiceImpl photoFileManagerService;
    private final FirebaseEducationServiceImpl educationService;
    private final FirebaseImageFileManagerService firebaseImageFileManagerService;

    @Value("${application.education.photos.upload.folder}")
    private String uploadFolder;

    @Autowired
    public EducationController(FirebaseEducationServiceImpl educationService,
                               FirebaseImageFileManagerService firebaseImageFileManagerService
                               //LocalEducationServiceImpl localEducationServiceImpl,
                               //LocalImageFileManagerServiceImpl photoFileManagerService
                              ) {
        //this.localEducationServiceImpl = localEducationServiceImpl;
        //this.photoFileManagerService = photoFileManagerService;
        this.educationService = educationService;
        this.firebaseImageFileManagerService = firebaseImageFileManagerService;
    }

    @GetMapping(path = "/educations",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Education> getEducations(){
        return this.educationService.getAllEducations();
    }

    @PostMapping(path = "/educations",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Education> addEducation(
            @RequestParam("logo") MultipartFile institutionLogo,
            @RequestParam("institutionName") String institutionName,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("year") String year,
            @RequestParam("duration") String duration,
            @RequestParam("hasTitle") boolean hasTitle,
            @RequestParam("titleName") String titleName,
            @RequestParam("description") String description
    ) {
        String  institutionLogoPath = firebaseImageFileManagerService.uploadFile(institutionLogo, uploadFolder);

        Education newEducation = educationService.addNewEducation(
                new Education(
                        name,
                        institutionName,
                        institutionLogoPath,
                        type,
                        year,
                        duration,
                        hasTitle,
                        titleName,
                        description
                )
        );

        return ResponseEntity.ok(newEducation);
    }

    @PutMapping(path = "/educations/{id}")
    public ResponseEntity<Education> editEducation(
            @PathVariable Long id,
            @RequestParam(value = "logo", required = false) MultipartFile institutionLogo,
            @RequestParam("institutionName") String institutionName,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("year") String year,
            @RequestParam("duration") String duration,
            @RequestParam("hasTitle") boolean hasTitle,
            @RequestParam("titleName") String titleName,
            @RequestParam("description") String description
    ){
        EducationDTO educationDTO = new EducationDTO();
        if(institutionLogo != null){
            String institutionLogoPath = firebaseImageFileManagerService.uploadFile(institutionLogo, uploadFolder);
            educationDTO.setInstitutionLogoPath(institutionLogoPath);
        }
        educationDTO.setName(name);
        educationDTO.setDescription(description);
        educationDTO.setDuration(duration);
        educationDTO.setHasTitle(hasTitle);
        educationDTO.setTitle(titleName);
        educationDTO.setInstitutionName(institutionName);
        educationDTO.setYear(year);
        educationDTO.setType(type);

        Education updatedEducation = this.educationService.editEducation(id, educationDTO);

        return ResponseEntity.ok(updatedEducation);
    }

    @DeleteMapping(path = "/educations/{id}")
    public ResponseEntity<Long> deleteEducation(@PathVariable Long id) throws Exception {
        Long deletedEducationId = this.educationService.deleteEducation(id);
        return ResponseEntity.ok(deletedEducationId);
    }

}

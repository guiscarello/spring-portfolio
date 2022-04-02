package gs.springportfolio.api;

import gs.springportfolio.dto.EducationDTO;
import gs.springportfolio.models.Education;
import gs.springportfolio.models.WorkExperience;
import gs.springportfolio.services.EducationServiceImpl;
import gs.springportfolio.services.PhotoFileManagerServiceImpl;
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

@Slf4j
@Getter
@Setter
@RequestMapping(path = "/api")
@RestController
public class EducationController {

    private final EducationServiceImpl educationServiceImpl;
    private final PhotoFileManagerServiceImpl photoFileManagerService;

    @Value("${application.education.photos.upload.folder}")
    private String uploadFolder;

    @Autowired
    public EducationController(EducationServiceImpl educationServiceImpl, PhotoFileManagerServiceImpl photoFileManagerService) {
        this.educationServiceImpl = educationServiceImpl;
        this.photoFileManagerService = photoFileManagerService;
    }

    @GetMapping(path = "/educations",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Education> getEducations(){
        return this.educationServiceImpl.getAllEducations();
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
        String  institutionLogoPath = photoFileManagerService.uploadFile(institutionLogo, uploadFolder);

        Education newEducation = educationServiceImpl.addNewEducation(
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
            String institutionLogoPath = photoFileManagerService.uploadFile(institutionLogo, uploadFolder);
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

        Education updatedEducation = this.educationServiceImpl.editEducation(id, educationDTO);

        return ResponseEntity.ok(updatedEducation);
    }

    @DeleteMapping(path = "/educations/{id}")
    public ResponseEntity<Long> deleteEducation(@PathVariable Long id) throws Exception {
        Long deletedEducationId = this.educationServiceImpl.deleteEducation(id);
        return ResponseEntity.ok(deletedEducationId);
    }

}

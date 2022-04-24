package gs.springportfolio.api;

import gs.springportfolio.dto.SkillDTO;
import gs.springportfolio.models.Skill;
import gs.springportfolio.services.files.FirebaseImageFileManagerService;
import gs.springportfolio.services.skills.FirebaseSkillServiceImpl;
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
public class SkillController {

    //private final LocalSkillServiceImpl skillService;
    //private final LocalImageFileManagerServiceImpl photoFileManagerService;
    private final FirebaseSkillServiceImpl skillService;
    private final FirebaseImageFileManagerService firebaseImageFileManagerService;
    @Value("${application.skill.photos.upload.folder}")
    private String uploadFolder;

    @Autowired
    public SkillController(//LocalSkillServiceImpl skillService,
                          // LocalImageFileManagerServiceImpl photoFileManagerService,
                           FirebaseImageFileManagerService firebaseImageFileManagerService,
                           FirebaseSkillServiceImpl skillService
                           ) {
        this.skillService = skillService;
        this.firebaseImageFileManagerService = firebaseImageFileManagerService;
        //this.photoFileManagerService = photoFileManagerService;
    }

    @GetMapping(path = "/skills",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Skill> getSkills(){

        return this.skillService.getAllSkills();
    }

    @PostMapping(path = "/skills",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Skill> addSkill(
            @RequestParam("logo") MultipartFile skillLogo,
            @RequestParam("name") String name,
            @RequestParam("level") String level,
            @RequestParam("levelPercentage") String percentage,
            @RequestParam("color") String color
    ) {
        //String  skillLogoPath = photoFileManagerService.uploadFile(skillLogo, uploadFolder);
        String  skillLogoPath = firebaseImageFileManagerService.uploadFile(skillLogo, uploadFolder);
        Skill newSkill = skillService.addNewSkill(
                new Skill(
                        name,
                        skillLogoPath,
                        level,
                        percentage,
                        color
                )
        );

        return ResponseEntity.ok(newSkill);
    }

    @PutMapping(path = "/skills/{id}")
    public ResponseEntity<Skill> editSkill(
            @PathVariable Long id,
            @RequestParam(value = "logo", required = false) MultipartFile skillLogo,
            @RequestParam("name") String name,
            @RequestParam("level") String level,
            @RequestParam("levelPercentage") String percentage,
            @RequestParam("color") String color

    ){
        SkillDTO skillDTO = new SkillDTO();
        if(skillLogo != null){
            //String skillLogoPath = photoFileManagerService.uploadFile(skillLogo, uploadFolder);
            String  skillLogoPath = firebaseImageFileManagerService.uploadFile(skillLogo, uploadFolder);
            skillDTO.setSkillLogoPath(skillLogoPath);
        }
        skillDTO.setName(name);
        skillDTO.setLevel(level);
        skillDTO.setLevelPercentage(percentage);
        skillDTO.setColor(color);

        Skill updatedSkill = this.skillService.editSkill(id, skillDTO);

        return ResponseEntity.ok(updatedSkill);
    }

    @DeleteMapping(path = "/skills/{id}")
    public ResponseEntity<Long> deleteSkill(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(this.skillService.deleteSkill(id));
    }
}

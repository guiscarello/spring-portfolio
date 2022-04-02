package gs.springportfolio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EducationDTO {

    private Long id;
    private String name;
    private String institutionName;
    private String institutionLogoPath = null;
    private String type;
    private String year;
    private String duration;
    private boolean hasTitle = false;
    private String title;
    private String description;

}

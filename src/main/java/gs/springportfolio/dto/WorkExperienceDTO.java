package gs.springportfolio.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class WorkExperienceDTO {

    private Long id;
    private String companyLogoPath = null;
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;
    private String tel;
    private boolean isCurrentWork;

}

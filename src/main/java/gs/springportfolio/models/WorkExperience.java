package gs.springportfolio.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Getter @Setter @NoArgsConstructor
@Entity
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String companyLogoPath;
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
    @Transient
    private Integer durationYears;
    @Transient
    private Integer durationMonths;
    private String position;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String tel;
    private boolean isCurrentWork;

    public WorkExperience(String companyLogo, String companyName,
                          LocalDate startDate, LocalDate endDate,
                          String position, String description, String tel,
                          boolean isCurrentWork) {
        this.companyName = companyName;
        this.companyLogoPath = companyLogo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
        this.tel = tel;
        this.isCurrentWork = isCurrentWork;
    }

    public Integer getDurationYears() {
        return Period.between(this.getStartDate(), this.getEndDate()).getYears();
    }

    public Integer getDurationMonths() {
       return Period.between(this.getStartDate(), this.getEndDate()).getMonths();
    }
}

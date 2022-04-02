package gs.springportfolio.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String institutionName;
    @Column(columnDefinition = "TEXT")
    private String institutionLogoPath;
    private String type;
    private String year;
    private String duration;
    private boolean hasTitle = Boolean.parseBoolean(null);
    private String title = null;
    @Column(columnDefinition = "TEXT")
    private String description;

    public Education(String name,
                     String institutionName,
                     String institutionLogoPath,
                     String type, String year,
                     String duration, Boolean hasTitle, String title, String description) {
        this.name = name;
        this.institutionName = institutionName;
        this.institutionLogoPath = institutionLogoPath;
        this.type = type;
        this.year = year;
        this.duration = duration;
        this.hasTitle = hasTitle;
        this.title = title;
        this.description = description;
    }
}

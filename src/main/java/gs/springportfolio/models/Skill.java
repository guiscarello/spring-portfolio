package gs.springportfolio.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String skillLogoPath;
    private String level;
    private String levelPercentage;
    private String color;
    @JsonIgnore
    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    public Skill(String name, String skillLogoPath, String level, String percentage, String color) {
        this.name = name;
        this.skillLogoPath = skillLogoPath;
        this.level = level;
        this.levelPercentage = percentage;
        this.color = color;
    }
}

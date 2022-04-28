package gs.springportfolio.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_skills",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "project", orphanRemoval = true)
    private List<ProjectPhoto> photos = new ArrayList<>();

    public Project(String title, LocalDate starDate, LocalDate endDate, String description, Set<Skill> skills, List<ProjectPhoto> photos) {
        this.title = title;
        this.startDate = starDate;
        this.endDate = endDate;
        this.description = description;
        this.skills = skills;
        this.photos = photos;
    }

    public void addSkill(Skill skill){
        this.skills.add(skill);
        skill.getProjects().add(this);
    }

    public void removeSkill(Skill skill){
        this.skills.remove(skill);
        skill.getProjects().remove(this);
    }

}

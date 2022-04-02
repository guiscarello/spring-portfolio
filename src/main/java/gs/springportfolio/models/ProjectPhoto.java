package gs.springportfolio.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Getter @Setter
@Entity
@NoArgsConstructor
public class ProjectPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String projectPhotoPath;
    private boolean isMainPhoto;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public ProjectPhoto(String projectPhotoPath, Boolean isMainPhoto) {
        this.projectPhotoPath = projectPhotoPath;
        this.isMainPhoto = isMainPhoto;
    }
}

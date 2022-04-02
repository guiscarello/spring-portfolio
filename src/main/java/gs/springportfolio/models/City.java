package gs.springportfolio.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double centroidLongitude;
    private double centroidLatitude;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    /*@OneToMany(mappedBy = "city")
    private List<User> users;*/
}

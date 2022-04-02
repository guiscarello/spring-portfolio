package gs.springportfolio.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "cen_longitude")
    private double centroidLongitude;
    @Column(name = "cen_latitude")
    private double centroidLatitude;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @OneToMany(mappedBy = "province")
    private List<Department> departmentList;

    public Province() {
    }

    public Province(String name, double centroidLongitude, double centroidLatitude) {
        this.name = name;
        this.centroidLongitude = centroidLongitude;
        this.centroidLatitude = centroidLatitude;
    }

}

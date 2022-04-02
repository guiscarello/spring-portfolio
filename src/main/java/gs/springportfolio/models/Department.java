package gs.springportfolio.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double centroidLongitude;
    private double centroidLatitude;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    @OneToMany(mappedBy = "department")
    private List<City> cityList;

    public Department() {
    }

    public Department(String name, double centroidLongitude, double centroidLatitude, Province province) {
        this.name = name;
        this.centroidLongitude = centroidLongitude;
        this.centroidLatitude = centroidLatitude;
        this.province = province;
    }
}

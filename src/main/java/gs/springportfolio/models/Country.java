package gs.springportfolio.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double centroidLongitude;
    private double centroidLatitude;
    @OneToMany(mappedBy = "country")
    private List<Province> provinceList;

    public Country() {
    }

    public Country(String name, double centroidLongitude, double centroidLatitude) {
        this.name = name;
        this.centroidLongitude = centroidLongitude;
        this.centroidLatitude = centroidLatitude;
    }

}

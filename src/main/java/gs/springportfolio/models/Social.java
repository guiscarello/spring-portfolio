package gs.springportfolio.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String bsClassIcon;
    private String link;

    public Social(String name, String bootstrapClass, String link) {
        this.name = name;
        this.bsClassIcon = bootstrapClass;
        this.link = link;
    }
}

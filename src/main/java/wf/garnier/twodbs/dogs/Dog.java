package wf.garnier.twodbs.dogs;

import lombok.*;
import wf.garnier.twodbs.Pet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class Dog implements Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Override
    public String getType() {
        return "dog";
    }
}

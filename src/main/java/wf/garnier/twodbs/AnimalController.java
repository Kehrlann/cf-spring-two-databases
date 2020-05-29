package wf.garnier.twodbs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wf.garnier.twodbs.cats.Cat;
import wf.garnier.twodbs.cats.CatRepository;
import wf.garnier.twodbs.dogs.DogRepository;

import java.util.ArrayList;
import java.util.Collection;


@RestController
public class AnimalController {

    private final CatRepository catRepository;
    private final DogRepository dogRepository;

    public AnimalController(CatRepository catRepository, DogRepository dogRepository) {
        this.catRepository = catRepository;
        this.dogRepository = dogRepository;
    }

    @GetMapping(value = "/")
    Collection<Pet> showPets() {
        ArrayList<Pet> pets = new ArrayList<>();
        pets.addAll(catRepository.findAll());
        pets.addAll(dogRepository.findAll());
        return pets;
    }
}

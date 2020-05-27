package wf.garnier.twodbs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wf.garnier.twodbs.cats.Cat;
import wf.garnier.twodbs.cats.CatRepository;

import java.util.Collection;

import static java.util.stream.Collectors.joining;

@RestController
public class AnimalController {

    private final CatRepository catRepository;

    public AnimalController(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @GetMapping(value = "/")
    Collection<Cat> showAnimals() {
        return catRepository.findAll();
    }
}

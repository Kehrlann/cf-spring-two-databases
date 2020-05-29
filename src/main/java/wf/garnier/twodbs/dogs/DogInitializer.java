package wf.garnier.twodbs.dogs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Component
public class DogInitializer implements CommandLineRunner {

    private final DogRepository repository;

    public DogInitializer(DogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.deleteAll();

        for (String name : asList("Bella", "Ziggy", "Oreo")) {
            Dog dog = new Dog().withName(name);
            repository.save(dog);
        }
    }
}

package wf.garnier.twodbs.cats;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Component
public class CatInitializer implements CommandLineRunner {

    private final CatRepository repository;

    public CatInitializer(CatRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.deleteAll();

        for (String name : asList("Mr Whiskers", "Charlemagne", "Pixel")) {
            Cat cat = new Cat().withName(name);
            repository.save(cat);
        }
    }
}

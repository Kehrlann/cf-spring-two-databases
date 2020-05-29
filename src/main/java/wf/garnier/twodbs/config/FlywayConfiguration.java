package wf.garnier.twodbs.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfiguration {
    @Bean
    public FlywayMigrationStrategy strategy(
            @Qualifier("catsDataSource") DataSource catsDataSource,
            @Qualifier("dogsDataSource") DataSource dogsDataSource
    ) {
        return (Flyway _defaultFlyway) -> {
            Flyway.configure()
                    .dataSource(catsDataSource)
                    .locations("db/cats/migration")
                    .load()
                    .migrate();

            Flyway.configure()
                    .dataSource(dogsDataSource)
                    .locations("db/dogs/migration")
                    .load()
                    .migrate();
        };
    }
}

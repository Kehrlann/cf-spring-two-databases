package wf.garnier.twodbs.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableJpaRepositories(
        basePackages = {"wf.garnier.twodbs.dogs"},
        entityManagerFactoryRef = "dogsEntityManager",
        transactionManagerRef = "dogsTransactionManager"
)
public class DogDatabaseConfiguration {

    @Bean
    public DataSourceProperties dogsDataSourceProperties(Optional<LocalDatabaseCredentials> credentials) {
        return credentials
                .map(LocalDatabaseCredentials::dogs)
                .orElseGet(() -> CfDatabaseCredentials.getDataSourcePropertiesForService("dogs-db"));
    }

    @Bean
    public HikariDataSource dogsDataSource(
            @Qualifier("dogsDataSourceProperties") DataSourceProperties dogsDataSourceProperties
    ) {
        return dogsDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean dogsEntityManager(
            @Qualifier("dogsDataSource") DataSource dogsDataSource,
            EntityManagerFactoryBuilder emFactoryBuilder
    ) {
        Map<String, String> properties = new HashMap<>();
        return emFactoryBuilder
                .dataSource(dogsDataSource)
                .packages("wf.garnier.twodbs.dogs")
                .persistenceUnit("dogsEntityManager")
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager dogsTransactionManager(
            @Qualifier("dogsEntityManager") EntityManagerFactory dogsEntityManager
    ) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(dogsEntityManager);
        return txManager;
    }
}
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
        basePackages = {"wf.garnier.twodbs.cats"},
        entityManagerFactoryRef = "catsEntityManager",
        transactionManagerRef = "catsTransactionManager"
)
public class CatDatabaseConfiguration {

    @Bean
    @Primary
    public DataSourceProperties catsDataSourceProperties(Optional<LocalDatabaseCredentials> credentials) {
        return credentials
                .map(LocalDatabaseCredentials::cats)
                .orElseGet(() -> CfDatabaseCredentials.getDataSourcePropertiesForService("cats-db"));
    }

    @Bean
    @Primary
    public HikariDataSource catsDataSource(
            @Qualifier("catsDataSourceProperties") DataSourceProperties catsDataSourceProperties
    ) {
        return catsDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean catsEntityManager(
            @Qualifier("catsDataSource") DataSource catsDataSource,
            EntityManagerFactoryBuilder emFactoryBuilder
    ) {
        Map<String, String> properties = new HashMap<>();
        return emFactoryBuilder
                .dataSource(catsDataSource)
                .packages("wf.garnier.twodbs.cats")
                .persistenceUnit("catsEntityManager")
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager catsTransactionManager(
            @Qualifier("catsEntityManager") EntityManagerFactory catsEntityManager
    ) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(catsEntityManager);
        return txManager;
    }
}
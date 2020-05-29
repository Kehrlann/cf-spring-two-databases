package wf.garnier.twodbs.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
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
    public DataSourceProperties catsDataSourceProperties(LocalDatabaseCredentials credentials) {
        return credentials.cats();
//        return credentials
//                .map(LocalDatabaseCredentials::getDatalakeProperties)
//                .orElseGet(() -> CfDatabaseCredentials.getDataSourcePropertiesForService("datalake-db"));
    }

    @Bean
//    @Primary
    public HikariDataSource catsDataSource(DataSourceProperties catsDataSourceProperties) {
        return catsDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
//    @Primary
    public LocalContainerEntityManagerFactoryBean catsEntityManager(DataSource catsDataSource, EntityManagerFactoryBuilder emFactoryBuilder) {
        Map<String, String> properties = new HashMap<>();
//        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        properties.put("hibernate.default_schema", schema);
//        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        return emFactoryBuilder
                .dataSource(catsDataSource)
                .packages("wf.garnier.twodbs.cats")
                .persistenceUnit("catsEntityManager")
                .properties(properties)
                .build();
    }

    @Bean
//    @Primary
    public PlatformTransactionManager catsTransactionManager(EntityManagerFactory catsEntityManager) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(catsEntityManager);
        return txManager;
    }
}
package wf.garnier.twodbs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Setter
@Component
@Profile("!cloud")
@ConfigurationProperties(prefix = "app.datasource")
public class LocalDatabaseCredentials {
    @NestedConfigurationProperty
    private DatabaseCredentials cats;
    @NestedConfigurationProperty
    private DatabaseCredentials dogs;

    public DataSourceProperties cats() {
        return buildDatasourceProperties(cats);
    }

    public DataSourceProperties dogs() {
        return buildDatasourceProperties(dogs);
    }

    private DataSourceProperties buildDatasourceProperties(DatabaseCredentials credentials) {
        DataSourceProperties props = new DataSourceProperties();
        props.setUrl(credentials.getUrl());
        props.setUsername(credentials.getUsername());
        props.setPassword(credentials.getPassword());
        return props;
    }

    @Getter
    @Setter
    static class DatabaseCredentials {
        private String url;
        private String username;
        private String password;
    }
}

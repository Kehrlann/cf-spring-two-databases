package wf.garnier.twodbs.config;

import io.pivotal.cfenv.jdbc.CfJdbcEnv;
import io.pivotal.cfenv.jdbc.CfJdbcService;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public class CfDatabaseCredentials {

    static DataSourceProperties getDataSourcePropertiesForService(String serviceName) {
        CfJdbcEnv cfenv = new CfJdbcEnv();
        CfJdbcService service = cfenv.findJdbcServiceByName(serviceName);

        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(service.getUrl());
        dataSourceProperties.setUsername(service.getUsername());
        dataSourceProperties.setPassword(service.getPassword());
//        dataSourceProperties.setDriverClassName(service.getDriverClassName().toString());
        return dataSourceProperties;
    }
}

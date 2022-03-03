package de.demiway.bonus.backend.bff;

import de.demiway.bonus.backend.domain.bonus.Bonus;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

import java.util.Map;

public class CustomHibernateBundle extends HibernateBundle<AppConfiguration> {

    protected CustomHibernateBundle() {
        super(
                Bonus.class
        );
    }

    @Override
    public DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
        var dataSourceFactory = configuration.getDataSourceFactory();
        if (!configuration.getEnvironment().isLocal) {
            dataSourceFactory.setProperties(Map.of(
                    "sslmode", "verify-full",
                    "sslrootcert", "/etc/ssl/certs/java/rds-combined-ca-bundle.pem"
            ));
        }

        return dataSourceFactory;
    }
}

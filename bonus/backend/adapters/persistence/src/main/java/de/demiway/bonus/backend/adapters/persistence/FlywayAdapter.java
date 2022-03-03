package de.demiway.bonus.backend.adapters.persistence;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayAdapter.class);
    private Flyway flyway;

    public FlywayAdapter(String connectionString) {
        var ds = new PGSimpleDataSource();
        ds.setUrl(connectionString);

        flyway = Flyway
                .configure()
                .cleanDisabled(true)
                .table("schema_version")
                .dataSource(ds)
                .schemas("public")
                .load();
    }

    public void migrate() {
        LOGGER.info("Starting flyway migration");
        flyway.migrate();
    }
}

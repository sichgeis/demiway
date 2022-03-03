package de.demiway.bonus.backend.bff.localdev;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class InMemoryDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryDatabase.class);

    public static String start() {
        var connectionString = "";
        try {
            var pg = EmbeddedPostgres.builder().setPort(6001).start();
            var databaseName = "postgres";
            var userName = "postgres";
            connectionString = pg.getJdbcUrl(userName, databaseName);
            // This level is WARN because the default log level of Dropwizard bootstrap is WARN.
            LOGGER.warn("Embedded Postgres started. Connect with {}", connectionString);
        } catch (IOException e) {
            LOGGER.error("Exception during start of embedded postgres", e);
            throw new RuntimeException("Exception during start of embedded postgres");
        }
        return connectionString;
    }
}

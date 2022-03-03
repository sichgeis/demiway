package de.demiway.bonus.backend.bff;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.demiway.bonus.backend.adapters.parameterstore.AwsParameterStoreFactory;
import de.demiway.bonus.backend.adapters.parameterstore.LocalParameterStore;
import de.demiway.bonus.backend.adapters.parameterstore.ParameterStore;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public class AppConfiguration extends Configuration {

    @Valid
    @NotNull
    private ParameterStore parameterStore;

    @Valid
    @NotNull
    private EnvironmentConfiguration environment;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    public AppConfiguration() {
        this.parameterStore = System.getenv("IS_LOCAL") != null ?
                new LocalParameterStore() :
                AwsParameterStoreFactory.createSecretsStore();
    }

    public ParameterStore getParameterStore() {
        return parameterStore;
    }

    @JsonProperty("database") public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database") public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
        this.database.setUrl(this.parameterStore.getParameter(ParameterStore.DB_CONNECTION_STRING));
    }

    public void setEnvironment(EnvironmentConfiguration environment) {
        this.environment = environment;
    }

    public EnvironmentConfiguration getEnvironment() {
        return environment;
    }

    public static class EnvironmentConfiguration {
        @NotNull
        public boolean isLocal;

        @NotNull
        public String cognitoDomain;

        public boolean allowLocalhostOrigin = false;
    }
}

package de.demiway.bonus.backend.bff;

import de.demiway.bonus.backend.adapters.parameterstore.ParameterStore;
import de.demiway.bonus.backend.adapters.persistence.FlywayAdapter;
import de.demiway.bonus.backend.adapters.persistence.repository.JpaBonusRepository;
import de.demiway.bonus.backend.bff.localdev.BonusFixture;
import de.demiway.bonus.backend.bff.localdev.InMemoryDatabase;
import de.demiway.bonus.backend.bff.resources.BonusResource;
import de.demiway.bonus.backend.bff.resources.HealthResource;
import de.demiway.bonus.backend.bff.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.MDC;

import java.util.UUID;

public class App extends Application<AppConfiguration> {

    private final HibernateBundle<AppConfiguration> hibernate = new CustomHibernateBundle();

    public static void main(final String[] args) throws Exception {
        MDC.put("correlation-id", UUID.randomUUID().toString());

        new App().run(args);
    }

    @Override
    public String getName() {
        return "bff";
    }

    @Override
    public void initialize(final Bootstrap<AppConfiguration> bootstrap) {

        if (System.getenv("IS_LOCAL") != null) {
            InMemoryDatabase.start();
        }

        bootstrap.addBundle(hibernate);
        allowEnvironmentVariableOverrides(bootstrap);
    }

    private static void allowEnvironmentVariableOverrides(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false))
        );
    }

    @Override
    public void run(final AppConfiguration appConfiguration, final Environment dropwizardEnv) {
        migrateDatabase(appConfiguration, dropwizardEnv);
        registerJerseyResources(appConfiguration, dropwizardEnv.jersey());
    }

    private void migrateDatabase(AppConfiguration appConfiguration, Environment dropwizardEnv) {
        var flywayAdapter = new FlywayAdapter(appConfiguration.getDataSourceFactory().getUrl());
        flywayAdapter.migrate();

        if (appConfiguration.getEnvironment().isLocal) {
            setupDummyData(dropwizardEnv);
        }
    }

    private void setupDummyData(Environment dropwizardEnv) {
        dropwizardEnv.lifecycle().manage(new BonusFixture(hibernate.getSessionFactory()));
    }

    private void registerJerseyResources(AppConfiguration appConfig, JerseyEnvironment jersey) {
        var sessionFactory = hibernate.getSessionFactory();

        jersey.register(new CorrelationIDFilter());
        jersey.register(new ResponseHeaderFilter(appConfig));
        jersey.register(new HealthResource());
        jersey.register(new BonusResource(new JpaBonusRepository(sessionFactory)));
        jersey.register(userResource(appConfig));
    }

    private UserResource userResource(AppConfiguration appConfiguration) {
        if (appConfiguration.getEnvironment().isLocal) {
            return new UserResource("", "");
        }

        var cognitoClientId = appConfiguration.getParameterStore().getParameter(ParameterStore.COGNITO_CLIENT_ID);
        var cognitoDomain = appConfiguration.getEnvironment().cognitoDomain;
        return new UserResource(cognitoClientId, cognitoDomain);
    }
}

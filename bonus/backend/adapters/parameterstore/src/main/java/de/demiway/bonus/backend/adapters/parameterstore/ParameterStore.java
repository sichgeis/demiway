package de.demiway.bonus.backend.adapters.parameterstore;

public interface ParameterStore {
    String DB_CONNECTION_STRING = "bonus.db.connection-string";

    String COGNITO_CLIENT_ID = "bonus.cognito.client-id";

    String getParameter(String parameter);
}

package de.demiway.bonus.backend.adapters.parameterstore;

public class LocalParameterStore implements ParameterStore {


    @Override
    public String getParameter(String parameter) {

        String result;
        switch (parameter) {
            case DB_CONNECTION_STRING:
                result = "jdbc:postgresql://localhost:6001/postgres?user=postgres";
                break;
            case COGNITO_CLIENT_ID:
                result = "";
                break;
            default:
                throw new IllegalArgumentException("Parameter " + parameter + " not found");
        }
        return result;
    }
}

package de.demiway.bonus.backend.adapters.parameterstore;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;

public class AwsParameterStore implements ParameterStore {

    private final SsmClient ssmClient;

    public AwsParameterStore(final SsmClient ssmClient) {
        this.ssmClient = ssmClient;
    }

    public String getParameter(final String key) {
        final var parameter = getParameterFromAws(key);
        if (parameter == null || parameter.isEmpty()) {
            throw new IllegalStateException(String.format("%s: Parameter %s not found in AWS.",
                    AwsParameterStore.class.getName(), key));
        }

        return parameter;
    }

    private String getParameterFromAws(final String nameOfTheParameter) {
        final var request = GetParameterRequest.builder()
                .name(nameOfTheParameter)
                .withDecryption(Boolean.TRUE)
                .build();
        final var result = ssmClient.getParameter(request);

        if (result == null || result.parameter() == null || result.parameter().value().isEmpty()) {
            return null;
        }

        return result.parameter().value();
    }
}

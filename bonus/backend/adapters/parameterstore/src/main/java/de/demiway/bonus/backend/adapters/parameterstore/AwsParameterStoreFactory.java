package de.demiway.bonus.backend.adapters.parameterstore;

import software.amazon.awssdk.services.ssm.SsmClient;

public final class AwsParameterStoreFactory {

    private AwsParameterStoreFactory() {
    }

    public static ParameterStore createSecretsStore() {
        return new AwsParameterStore(createSSMClient());
    }

    private static SsmClient createSSMClient() {
        return SsmClient.builder().build();
    }
}

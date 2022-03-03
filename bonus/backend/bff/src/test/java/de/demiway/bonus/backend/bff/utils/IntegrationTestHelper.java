package de.demiway.bonus.backend.bff.utils;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTestHelper {

    public static void expectStatusCode(Response response, int i) {
        assertThat(response.getStatus()).isEqualTo(i);
    }
}

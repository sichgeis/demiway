package de.demiway.bonus.backend.bff.resources;


import de.demiway.bonus.backend.bff.utils.IntegrationTestHelper;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

class HealthResourceTest {

    private ResourceExtension testRule;

    @BeforeEach
    void setUp() throws Throwable {
        testRule = testRule();
        testRule.before();
    }

    @Test
    void returns200() {
        var response = response();

        IntegrationTestHelper.expectStatusCode(response, 200);
    }

    private ResourceExtension testRule() {
        return ResourceExtension.builder()
                .addResource(new HealthResource())
                .build();
    }

    private Response response() {
        return testRule.target("/health")
                .request().get();
    }
}

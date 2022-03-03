package de.demiway.bonus.backend.bff.resources;

import de.demiway.bonus.backend.bff.auth.Identity;
import de.demiway.bonus.backend.bff.utils.IntegrationTestHelper;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("SameParameterValue")
class UserResourceTest {

    private ResourceExtension testRule;

    @BeforeEach
    void setUp() throws Throwable {
        testRule = testRule();
        testRule.before();
    }

    @Test
    void suppliesLogoutEndpoint() {

        var response = testRule.client().target("/user/logout").request().get();

        IntegrationTestHelper.expectStatusCode(response, 200);
        assertThat(responseAsString(response)).contains("logout");
    }

    @Test
    void suppliesIdentity() {

        var response = testRule.client().target("/user/identity").request().get();

        var identity = response.readEntity(Identity.class);

        IntegrationTestHelper.expectStatusCode(response, 200);
        assertThat(identity).isEqualTo(Identity.unidentified());
    }

    private static String responseAsString(Response response) {
        return response.readEntity(String.class);
    }

    private ResourceExtension testRule() {
        return ResourceExtension.builder()
                .addResource(new UserResource("", ""))
                .build();
    }
}
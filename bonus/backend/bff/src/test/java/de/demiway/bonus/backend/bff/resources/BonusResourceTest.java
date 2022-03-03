package de.demiway.bonus.backend.bff.resources;

import de.demiway.bonus.backend.adapters.persistence.repository.JpaBonusRepository;
import de.demiway.bonus.backend.domain.bonus.Bonus;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static de.demiway.bonus.backend.bff.utils.IntegrationTestHelper.expectStatusCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BonusResourceTest {
    @Mock
    private JpaBonusRepository BONUS_REPOSITORY;

    @Captor
    private ArgumentCaptor<Bonus> bonusCaptor;
    public ResourceExtension RESOURCES;
    public static final String BONUS_BASE_PATH = "/bonuses";

    @BeforeEach
    void setUp() throws Throwable {
        MockitoAnnotations.openMocks(this);
        RESOURCES = ResourceExtension.builder()
                .addResource(new BonusResource(BONUS_REPOSITORY))
                .build();
        RESOURCES.before();
    }

    @Test
    void validationErrorReturns422() {
        var invalidBonus = Bonus.builder()
                .build();

        final var response = RESOURCES.target(BONUS_BASE_PATH)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(invalidBonus, MediaType.APPLICATION_JSON_TYPE));

        expectStatusCode(response, 422);
        verify(BONUS_REPOSITORY, never()).create(any());
    }

    @Test
    void returns422OnNullObject() {
        final var response = RESOURCES.target(BONUS_BASE_PATH)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(null, MediaType.APPLICATION_JSON_TYPE));

        expectStatusCode(response, 422);
        verify(BONUS_REPOSITORY, never()).create(any());
    }
}

package de.demiway.bonus.backend.adapters.parameterstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.Parameter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AwsParameterStoreTest {

    private SsmClient mockedSSMClient;

    @BeforeEach
    void setUp() {
        mockedSSMClient = mock(SsmClient.class);
    }

    @Test
    void keyNotFoundTest() {

        // Arrange
        final var sut = new AwsParameterStore(mockedSSMClient);

        // Act
        assertThrows(IllegalStateException.class, () -> sut.getParameter("someKey"));
    }

    @Test
    void keyFoundTest() {

        // Arrange
        final var mockedResult = mock(GetParameterResponse.class);
        final var mockedParameter = mock(Parameter.class);
        when(mockedParameter.value()).thenReturn("specialValue");
        when(mockedResult.parameter()).thenReturn(mockedParameter);
        when(mockedSSMClient.getParameter(any(GetParameterRequest.class))).thenReturn(mockedResult);
        final var sut = new AwsParameterStore(mockedSSMClient);

        // Act
        final var result = sut.getParameter("someKey");

        // Assert
        verify(mockedSSMClient, times(1)).getParameter((GetParameterRequest) any());
        assertEquals(result, "specialValue", "wrong key found");
    }
}

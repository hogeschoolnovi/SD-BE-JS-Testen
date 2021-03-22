package nl.novi.mockitoexample.integration;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IntegrationTest {

    @Test
    void test() {
        Assertions.assertTrue(true);
    }
}

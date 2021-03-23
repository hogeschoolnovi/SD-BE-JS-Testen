package nl.novi.mockitoexample.service;

import nl.novi.mockitoexample.payload.request.AddressRequest;
import nl.novi.mockitoexample.payload.request.SignupRequest;
import nl.novi.mockitoexample.payload.response.ErrorResponse;
import nl.novi.mockitoexample.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


/**
 * In deze test-klasse testen we de PersonServiceImpl zonder de hele Spring-boot applicatie op te starten. Dit doen we
 * door <code>@ExtendWith(MockitoExtension.class)</code> te gebruiken.
 */
@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    // Verschil Mock en InjectMocks
    // https://howtodoinjava.com/mockito/mockito-mock-injectmocks/#:~:text=In%20mockito%20based%20junit%20tests,and%20%40InjectMocks%20creates%20class%20objects.&text=Use%20%40InjectMocks%20to%20create%20class,be%20tested%20in%20test%20class.&text=Use%20%40Mock%20to%20create%20mocks,of%20class%20to%20be%20tested.

    @InjectMocks
    private final PersonService personService = new PersonServiceImpl();

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressRequest addressRequest;

    private SignupRequest signupRequest;

    @BeforeEach
    void setup() {
        signupRequest = new SignupRequest();
        signupRequest.setFirstName("Nick");
        signupRequest.setLastName("NOVI");
        signupRequest.setEmail("nick@novi.nl");
        signupRequest.setUsername("nicknick");
        signupRequest.setPassword("password");
        signupRequest.setRepeatedPassword("password");
    }

    @Test
    void unmatchingPasswordShouldReturnError() {
        // Arrange
        signupRequest.setRepeatedPassword("completelyWrongPassword");

        // Act
        ResponseEntity<?> responseEntity = personService.registerWithoutAddress(signupRequest);

        // Assert
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
        Assertions.assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size());
        Assertions.assertTrue(((((ErrorResponse) responseEntity.getBody()).getErrors()).containsKey("repeatedPassword")));
        Assertions.assertSame("The passwords do not match eachother.", ((ErrorResponse) responseEntity.getBody()).getErrors().get("repeatedPassword"));

    }

    @Test
    void nonUniqueUsernameShouldReturnError() {
        // Arrange
        Mockito.when(personRepository.existsByUsername(signupRequest.getUsername())).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = personService.registerWithoutAddress(signupRequest);

        // Assert
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
        Assertions.assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size());

        Assertions.assertTrue(((((ErrorResponse) responseEntity.getBody()).getErrors()).containsKey("username")));
        Assertions.assertSame("The username already exists.", ((ErrorResponse) responseEntity.getBody()).getErrors().get("username"));
    }

    @Test
    void nonUniqueEmailShouldReturnError() {
        Mockito.when(personRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        ResponseEntity<?> responseEntity = personService.registerWithoutAddress(signupRequest);

        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
        Assertions.assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size());

        Assertions.assertTrue(((((ErrorResponse) responseEntity.getBody()).getErrors()).containsKey("email")));
        Assertions.assertSame("The email is already in use.", ((ErrorResponse) responseEntity.getBody()).getErrors().get("email"));
    }

    @Test
    void nonUniqueUsernameAndEmailShouldReturnMultipleErrors() {
        Mockito.when(personRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);
        Mockito.when(personRepository.existsByUsername(signupRequest.getUsername())).thenReturn(true);

        ResponseEntity<?> responseEntity = personService.registerWithoutAddress(signupRequest);

        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
        Assertions.assertEquals(2, ((ErrorResponse) responseEntity.getBody()).getErrors().size());

        Assertions.assertTrue(((((ErrorResponse) responseEntity.getBody()).getErrors()).containsKey("username")));
        Assertions.assertSame("The username already exists.", ((ErrorResponse) responseEntity.getBody()).getErrors().get("username"));
        Assertions.assertTrue(((((ErrorResponse) responseEntity.getBody()).getErrors()).containsKey("email")));
        Assertions.assertSame("The email is already in use.", ((ErrorResponse) responseEntity.getBody()).getErrors().get("email"));
    }

    @Test
    void addingAddressToUnknownIdShouldReturnErrorMessage() {

        long id = 1L;

        Mockito.when(personRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = personService.addAddressToUserById(id, addressRequest);

        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
        Assertions.assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size());

        Assertions.assertTrue(((((ErrorResponse) responseEntity.getBody()).getErrors()).containsKey("id")));

        Assertions.assertEquals("The user with id (" + id + ") does not exist.",
                ((ErrorResponse) responseEntity.getBody()).getErrors().get("id"));
    }

}

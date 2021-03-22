package nl.novi.mockitoexample.service;

import nl.novi.mockitoexample.payload.request.AddressRequest;
import nl.novi.mockitoexample.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface PersonService {

    ResponseEntity<?> printUser(SignupRequest signupRequest);
    ResponseEntity<?> registerWithoutAddress(SignupRequest signupRequest);
    ResponseEntity<?> addAddressToUserById(long id, AddressRequest addressRequest);
    ResponseEntity<?> getPersonInfoById(long id);
}

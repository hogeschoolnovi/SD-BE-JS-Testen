package nl.novi.mockitoexample.service;

import nl.novi.mockitoexample.domain.Address;
import nl.novi.mockitoexample.domain.Person;
import nl.novi.mockitoexample.payload.request.AddressRequest;
import nl.novi.mockitoexample.payload.request.SignupRequest;
import nl.novi.mockitoexample.payload.response.ErrorResponse;
import nl.novi.mockitoexample.payload.response.PersonResponse;
import nl.novi.mockitoexample.repository.AddressRepository;
import nl.novi.mockitoexample.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private AddressRepository addressRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public ResponseEntity<?> printUser(SignupRequest signupRequest) {
        return ResponseEntity.ok(signupRequest);
    }

    @Override
    public ResponseEntity<?> registerWithoutAddress(SignupRequest signupRequest) {

        Person person = new Person();
        ErrorResponse errorResponse = new ErrorResponse();

        if(signupRequest.getFirstName() != null && !signupRequest.getFirstName().equals("")) {
            person.setFirstName(signupRequest.getFirstName());
        }

        if(signupRequest.getLastName() != null && !signupRequest.getLastName().equals("")) {
            person.setLastName(signupRequest.getLastName());
        }

        if(!signupRequest.getPassword().equals(signupRequest.getRepeatedPassword())) {
            errorResponse.addError("repeatedPassword", "The passwords do not match eachother.");
        }
        if(personRepository.existsByUsername(signupRequest.getUsername())) {
            errorResponse.addError("username", "The username already exists.");
        }
        if(personRepository.existsByEmail(signupRequest.getEmail())) {
            errorResponse.addError("email", "The email is already in use.");
        }

        person.setEmail(signupRequest.getEmail());
        person.setUsername(signupRequest.getUsername());
        person.setPassword(signupRequest.getPassword());

        if(errorResponse.hasErrors()) {
            return ResponseEntity.status(500).body(errorResponse);
        }

        Person savedPerson = personRepository.save(person);

        return ResponseEntity.ok(createResponseObject(savedPerson));
    }

    @Override
    public ResponseEntity<?> addAddressToUserById(long id, AddressRequest addressRequest) {


        Optional<Person> optionalPerson = personRepository.findById(id);

        if(optionalPerson.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.addError("id", "The user with id (" + id + ") does not exist.");
            return ResponseEntity.status(500).body(errorResponse);
        }

        Person personWithAddress = optionalPerson.get();
        Address address = new Address();
        address.setPostalCode(addressRequest.getPostalCode());
        address.setHouseNumber(addressRequest.getHouseNumber());
        address.setStreetName(addressRequest.getStreetName());
        if(addressRequest.getAddition() != null && !addressRequest.getAddition().equals("")) {
            address.setAddition(addressRequest.getAddition());
        }
        address.setPerson(personWithAddress);

        addressRepository.save(address);

        personWithAddress.setAddress(address);

        return ResponseEntity.ok(createResponseObject(personWithAddress));
    }

    @Override
    public ResponseEntity<?> getPersonInfoById(long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Person> optionalPerson = personRepository.findById(id);

        if(optionalPerson.isEmpty()) {
            errorResponse.addError("id", "The user with id (" + id + ") does not exist.");
            return ResponseEntity.status(500).body(errorResponse);
        }
        
        Person person = optionalPerson.get();

        PersonResponse personResponse = createResponseObject(person);

        return ResponseEntity.ok(personResponse);
    }

    private PersonResponse createResponseObject(Person person) {
        PersonResponse personResponse = new PersonResponse(person.getId(),
                person.getUsername(), person.getEmail());

        if(person.getFirstName() != null && !person.getFirstName().equals("")) {
            personResponse.setFirstName(person.getFirstName());
        }
        if(person.getLastName() != null && !person.getLastName().equals("")) {
            personResponse.setLastName(person.getLastName());
        }

        if(person.getAddress() != null) {
            Address address = person.getAddress();
            personResponse.setAddress(address.getPostalCode(), address.getStreetName(), address.getHouseNumber());
            if(address.getAddition()!= null && !address.getAddition().equals("")) {
                personResponse.setAddition(address.getAddition());
            }
        }
        return personResponse;
    }
}

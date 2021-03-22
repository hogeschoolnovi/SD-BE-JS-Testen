package nl.novi.mockitoexample.repository;

import nl.novi.mockitoexample.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

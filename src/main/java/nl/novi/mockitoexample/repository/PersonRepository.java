package nl.novi.mockitoexample.repository;

import nl.novi.mockitoexample.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}

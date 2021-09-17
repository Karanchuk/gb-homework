package gb.SpringRest_1.repository;

import gb.SpringRest_1.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c from Customer c where LOWER(c.name) = LOWER(?1)")
    List<Customer> findByName(String name);

}

package gb.Hibernate2.service;

import gb.Hibernate2.model.Customer;
import gb.Hibernate2.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerRepositoryService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.getAll();
    }

    public Customer getByID(Integer id) {
        return customerRepository.getByID(id);
    }

    public List<Customer> getByName(String name) {
        return customerRepository.getByName(name);
    }

    public void addNew(Customer customer) {
        customerRepository.addNew(customer);
    }
}

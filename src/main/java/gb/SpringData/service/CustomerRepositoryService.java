package gb.SpringData.service;

import gb.SpringData.model.Customer;
import gb.SpringData.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerRepositoryService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getByID(Integer id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getByName(String name) {
        return customerRepository.findByName(name);
    }

    public void addNew(Customer customer) {
        customerRepository.saveAndFlush(customer);
    }
}

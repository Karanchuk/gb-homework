package gb.Thymeleaf.service;

import gb.Thymeleaf.dto.CustomerDto;
import gb.Thymeleaf.dto.ProductDto;
import gb.Thymeleaf.model.Customer;
import gb.Thymeleaf.model.Product;
import gb.Thymeleaf.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerRepositoryService {
    private final CustomerRepository customerRepository;

    public List<CustomerDto> getAll() {
        return customerRepository.findAll().stream().map(CustomerDto::valueOf).collect(Collectors.toList());
    }

    public Optional<CustomerDto> getByID(Integer id) {
        return customerRepository.findById(id).stream().map(CustomerDto::valueOf).findFirst();
    }

    public List<CustomerDto> getByName(String name) {
        return customerRepository.findByName(name).stream().map(CustomerDto::valueOf).collect(Collectors.toList());
    }

    public void addNew(CustomerDto customerDto) {
        customerRepository.saveAndFlush(customerDto.mapToCustomer());
    }

    public void newSale(CustomerDto customerDto, ProductDto productDto) {
        Customer customer = customerDto.mapToCustomer();
        List<Product> products = customer.getProducts();
        products.add(productDto.mapToProduct());
        customer.setProducts(products);
        customerRepository.saveAndFlush(customer);
    }

    public void removeById(int id) {
        customerRepository.deleteById(id);
    }
}

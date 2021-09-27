package store.service;

import store.dto.CustomerDto;
import store.dto.ProductDto;
import store.exception.CustomerNotFoundException;
import store.model.Customer;
import store.model.Product;
import store.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerRepositoryService {
    private final CustomerRepository customerRepository;
    private final ProductRepositoryService productRepositoryService;

    public List<CustomerDto> getAll() {
        return customerRepository.findAll().stream().map(CustomerDto::valueOf).collect(Collectors.toList());
    }

    public CustomerDto getByID(Integer id) {
        return customerRepository.findById(id).stream().map(CustomerDto::valueOf).findFirst().orElseThrow(CustomerNotFoundException::new);
    }

    public List<CustomerDto> getByName(String name) {
        return customerRepository.findByName(name).stream().map(CustomerDto::valueOf).collect(Collectors.toList());
    }

    public void addNew(CustomerDto customerDto) {
        customerRepository.saveAndFlush(customerDto.mapToCustomer());
    }

    public void newSale(Integer customer_id, Integer product_id) {
        CustomerDto customerDto = getByID(customer_id);
        Customer customer;
        customer = customerDto.mapToCustomer();
        Optional<ProductDto> productDto = productRepositoryService.getWithFilter(Optional.ofNullable(product_id), null, null).stream().findFirst();

        Product product;
        if (productDto.isPresent())
            product = productDto.get().mapToProduct();
        else
            return;

        List<Product> products = customer.getProducts();
        products.add(product);
        customer.setProducts(products);
        customerRepository.saveAndFlush(customer);
    }

    public void removeById(int id) {
        customerRepository.deleteById(id);
    }

    public void update(int id, String name) {
        customerRepository.findById(id).ifPresent(p -> p.setName(name));
    }
}
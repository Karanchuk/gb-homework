package gb.SpringRest_1.service;

import gb.SpringRest_1.dto.CustomerDto;
import gb.SpringRest_1.dto.ProductDto;
import gb.SpringRest_1.model.Customer;
import gb.SpringRest_1.model.Product;
import gb.SpringRest_1.repository.CustomerRepository;
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

    public Optional<CustomerDto> getByID(Integer id) {
        return customerRepository.findById(id).stream().map(CustomerDto::valueOf).findFirst();
    }

    public List<CustomerDto> getByName(String name) {
        return customerRepository.findByName(name).stream().map(CustomerDto::valueOf).collect(Collectors.toList());
    }

    public void addNew(CustomerDto customerDto) {
        customerRepository.saveAndFlush(customerDto.mapToCustomer());
    }

    public void newSale(Integer customer_id, Integer product_id) {
        Optional<CustomerDto> customerDto = getByID(customer_id);
        Customer customer;
        if (customerDto.isPresent())
            customer = customerDto.get().mapToCustomer();
        else
            return;

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

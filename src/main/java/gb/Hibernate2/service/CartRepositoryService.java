package gb.Hibernate2.service;

import gb.Hibernate2.model.Customer;
import gb.Hibernate2.model.Product;
import gb.Hibernate2.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartRepositoryService {

    private final CartRepository cartRepository;

    public void newSale(Customer customer, Product product) {
        cartRepository.newSale(customer,product);
    }

}

package gb.SpringData.service;

import gb.SpringData.model.Customer;
import gb.SpringData.model.Product;
import gb.SpringData.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartRepositoryService {

    private final CartRepository cartRepository;

    public void newSale(Customer customer, Product product) {
        cartRepository.newSale(customer,product);
    }

}

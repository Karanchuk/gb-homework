package gb.Hibernate2.service;

import gb.Hibernate2.model.Product;
import gb.Hibernate2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRepositoryService {
    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.getAll();
    }

    public Product getById(Integer id) {
        return productRepository.getById(id);
    }

    public void addNew(Product product) {
        productRepository.addNew(product);
    }

    public void removeById(int id) {
        productRepository.remove(id);
    }

    public void updatePrice(int id, int cost) {
        productRepository.updatePrice(id, cost);
    }
}
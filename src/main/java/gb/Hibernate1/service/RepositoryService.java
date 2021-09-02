package gb.Hibernate1.service;

import gb.Hibernate1.model.Product;
import gb.Hibernate1.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final Repository productRepository;

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

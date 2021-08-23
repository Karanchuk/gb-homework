package lesson3.service;

import lesson3.repository.Repository;
import lesson3.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final Repository productRepository;

    public List<Product> getAll() {
        return productRepository.getAll();
    }

    public Product getById(int id) {
        return productRepository.getById(id);
    }

    public void addNew(Product product) {
        productRepository.addNew(product);
    }
}

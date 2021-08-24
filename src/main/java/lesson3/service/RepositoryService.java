package lesson3.service;

import lesson3.repository.Repository;
import lesson3.model.Product;
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
        return productRepository.getById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public void addNew(Product product) {
        productRepository.addNew(product);
    }
}

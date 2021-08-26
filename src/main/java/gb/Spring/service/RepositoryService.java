package gb.Spring.service;

import gb.Spring.model.Product;
import gb.Spring.repository.Repository;
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

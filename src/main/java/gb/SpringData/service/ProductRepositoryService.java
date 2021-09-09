package gb.SpringData.service;

import gb.SpringData.model.Product;
import gb.SpringData.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductRepositoryService {
    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    public void addNew(Product product) {
        productRepository.saveAndFlush(product);
    }

    public void removeById(int id) {
        productRepository.deleteAllByIdInBatch(Collections.singleton(id));
    }

    public void updatePrice(int id, int cost) {
        productRepository.findById(id).ifPresent(p -> p.setCost(cost));
    }

    public List<Product> getWithFilter(Optional<String> title, Optional<Integer> cost) {
        return productRepository.findWithFilter(title, cost);
    }
}
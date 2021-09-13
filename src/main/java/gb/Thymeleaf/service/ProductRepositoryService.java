package gb.Thymeleaf.service;

import gb.Thymeleaf.dto.ProductDto;
import gb.Thymeleaf.model.Product;
import gb.Thymeleaf.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductRepositoryService {
    private final ProductRepository productRepository;

    public List<ProductDto> getAll() {
        return productRepository.findAll().stream().map(ProductDto::valueOf).collect(Collectors.toList());
    }

    public Optional<ProductDto> getById(Integer id) {
        return productRepository.findById(id).stream().map(ProductDto::valueOf).findFirst();
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
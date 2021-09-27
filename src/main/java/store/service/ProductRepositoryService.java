package store.service;

import store.dto.ProductDto;
import store.repository.ProductRepository;
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

    public void addNew(ProductDto productDto) {
        productRepository.saveAndFlush(productDto.mapToProduct());
    }

    public void removeById(int id) {
        productRepository.deleteAllByIdInBatch(Collections.singleton(id));
    }

    public void updatePrice(int id, int cost) {
        productRepository.findById(id).ifPresent(p -> p.setCost(cost));
    }

    public List<ProductDto> getWithFilter(Optional<Integer> id, Optional<String> title, Optional<Integer> cost) {
        return productRepository.findWithFilter(id, title, cost).stream().map(ProductDto::valueOf).collect(Collectors.toList());
    }

}
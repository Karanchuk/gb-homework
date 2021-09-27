package store.unit;

import store.BaseTest;
import store.dto.ProductDto;
import store.repository.ProductRepository;
import store.service.ProductRepositoryService;
import org.assertj.core.api.AssertProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = ProductRepositoryService.class)
public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductRepositoryService productRepositoryService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void findAllSuccess() {
        Mockito.doReturn(Collections.emptyList()).when(productRepository).findAll();
        List<ProductDto> actual = productRepositoryService.getAll();
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    public void findByIdWithEmptyResult() {
        Integer id = 1;
        Mockito.doReturn(Optional.empty()).when(productRepository).findById(id);
        Assertions.assertThat((AssertProvider<Boolean>) () -> productRepositoryService.getWithFilter(Optional.of(id), Optional.empty(), Optional.empty()).isEmpty());
    }
}
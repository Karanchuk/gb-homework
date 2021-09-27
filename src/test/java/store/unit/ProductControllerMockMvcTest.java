package store.unit;

import store.controller.ProductController;
import store.dto.ProductDto;
import store.model.Customer;
import store.model.Product;
import store.repository.ProductRepository;
import store.service.ProductRepositoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerMockMvcTest {

    private static final String BASE_URL = "/api/v1/product";

    @Autowired
    private MockMvc mvc;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @MockBean
    private ProductRepositoryService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findAllSuccess() throws Exception {
        Product product = new Product();
        product.setTitle("test");
        product.setCost(100);
        Product savedProduct = productRepository.save(product);

        Mockito.doReturn(List.of(savedProduct)).when(productService).getWithFilter(
                Optional.of(savedProduct.getId()),
                Optional.of(savedProduct.getTitle()),
                Optional.of(savedProduct.getCost()));

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":" + savedProduct.getId() + ",\"title\":\"" + savedProduct.getTitle() + "\",\"cost\":" + savedProduct.getCost() + "}]", true));

    }
}
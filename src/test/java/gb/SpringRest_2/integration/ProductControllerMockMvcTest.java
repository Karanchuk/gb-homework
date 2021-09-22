package gb.SpringRest_2.integration;

import gb.SpringRest_2.dto.ProductDto;
import gb.SpringRest_2.model.Product;
import gb.SpringRest_2.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author antonkuznetsov
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerMockMvcTest {

    private static final String BASE_URL = "/api/v1/product";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mvc;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Успешное получение всех значений")
    public void findAllSuccess() throws Exception {
        Product product = new Product();
        product.setTitle("test");
        product.setCost(100);
        Product savedProduct = productRepository.save(product);
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"id\":" + savedProduct.getId()
                        + ",\"title\":\"" + savedProduct.getTitle()
                                + "\",\"cost\":" + savedProduct.getCost() + "}]",
                        true));

    }

    @Test
    @DisplayName("Успешный поиск по идентфикатору")
    public void findByIdSuccess() throws Exception {
        Product product = new Product();
        product.setTitle("Avocado");
        product.setCost(200);
        Product savedProduct = productRepository.save(product);
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?id=" + savedProduct.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"id\":" + savedProduct.getId()
                                + ",\"title\":\"" + savedProduct.getTitle()
                                + "\",\"cost\":" + savedProduct.getCost() + "}]",
                        true));
    }

    @Test
    @DisplayName("Ошибка поиска по идентфикатору")
    public void findByIdFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?id=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[]",
                        true));
    }

}

package gb.SpringRest_2.integration;

import gb.SpringRest_2.model.Product;
import gb.SpringRest_2.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

}

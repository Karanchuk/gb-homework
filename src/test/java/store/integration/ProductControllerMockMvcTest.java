package store.integration;

import store.dto.ProductDto;
import store.model.Product;
import store.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static additional.ForTest.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(status().isOk())
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
                .andExpect(status().isOk())
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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[]",
                        true));
    }

    @Test
    @DisplayName("Успешное создание объекта")
    public void createSuccess() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setTitle("test");
        productDto.setCost(100);

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Неправильный запрос при создании объекта")
    public void createFail() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString("test"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Успешное обновление цены")
    public void costUpdateSuccess() throws Exception {
        Product product = new Product();
        product.setTitle("Avocado");
        product.setCost(200);
        Product savedProduct = productRepository.save(product);

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + savedProduct.getId() + "/300")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .accept(MediaType.APPLICATION_XML)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        //        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?id=" + savedProduct.getId()))
        //                .andDo(MockMvcResultHandlers.print())
        //                .andExpect(status().isOk())
        //                .andExpect(MockMvcResultMatchers.content().json(
        //                        "[{\"id\":" + savedProduct.getId()
        //                                + ",\"title\":\"" + savedProduct.getTitle()
        //                                + "\",\"cost\":300}]",
        //                        true));

    }

    @Test
    @DisplayName("Успешное удаление объекта")
    public void deleteSuccess() throws Exception {
        Product product = new Product();
        product.setTitle("Avocado");
        product.setCost(200);
        Product savedProduct = productRepository.save(product);

        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL+ "/{id}", savedProduct.getId()))
                .andExpect(status().isNotFound());
    }

}
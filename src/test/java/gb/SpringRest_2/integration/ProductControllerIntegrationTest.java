package gb.SpringRest_2.integration;


import gb.SpringRest_2.model.Product;
import gb.SpringRest_2.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    private static final String BASE_URL = "/api/v1/product";
    @Autowired
    ProductRepository productRepository;

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Успешный поиск по идентфикатору")
    public void findByIdSuccess() {
        Product product = new Product();
        product.setTitle("Avocado");
        product.setCost(200);
        Product savedProduct = productRepository.save(product);
        ResponseEntity<Product> actual = restTemplate.getForEntity("http://localhost:" + port + BASE_URL + "/" + savedProduct.getId(), Product.class);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(actual.getBody()).isNotNull();
    }

    @Test
    public void findByIdFail() {
        ResponseEntity<Product> actual = restTemplate.getForEntity("http://localhost:" + port + BASE_URL + "/1", Product.class);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(actual.getBody()).isNotNull();
    }

}

package store.integration;

import store.model.Customer;
import store.repository.CustomerRepository;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

    private static final String BASE_URL = "/api/v1/customer";
    @Autowired
    CustomerRepository customerRepository;

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Успешный поиск по идентфикатору")
    public void findByIdSuccess() {
        Customer customer = new Customer();
        customer.setName("Alice");
        Customer savedCustomer = customerRepository.save(customer);
        ResponseEntity<Customer> actual = restTemplate.getForEntity("http://localhost:" + port + BASE_URL + "?id=" + savedCustomer.getId(), Customer.class);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(actual.getBody()).isNotNull();
    }

    @Test
    @DisplayName("Ошибка поиска по идентфикатору")
    public void findByIdFail() {
        ResponseEntity<Customer> actual = restTemplate.getForEntity("http://localhost:" + port + BASE_URL + "?id=1", Customer.class);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(actual.getBody()).isNull();
    }
}
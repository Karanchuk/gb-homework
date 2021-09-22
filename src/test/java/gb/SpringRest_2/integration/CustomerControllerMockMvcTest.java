package gb.SpringRest_2.integration;

import gb.SpringRest_2.model.Customer;
import gb.SpringRest_2.model.Product;
import gb.SpringRest_2.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
public class CustomerControllerMockMvcTest {

    private static final String BASE_URL = "/api/v1/customer";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mvc;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Успешное получение всех значений")
    public void findAllSuccess() throws Exception {
        Customer customer = new Customer();
        customer.setName("test");
        Customer savedCustomer = customerRepository.save(customer);
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"id\":" + savedCustomer.getId()
                        + ",\"name\":\"" + savedCustomer.getName() + "\"}]",
                        true));

    }

    @Test
    @DisplayName("Успешный поиск по идентфикатору")
    public void findByIdSuccess() throws Exception {
        Customer customer = new Customer();
        customer.setName("test");
        Customer savedCustomer = customerRepository.save(customer);
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?id=" + savedCustomer.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\"id\":" + savedCustomer.getId()
                                + ",\"name\":\"" + savedCustomer.getName() + "\"}",
                        true));
    }

    @Test
    @DisplayName("Ошибка поиска по идентфикатору")
    public void findByIdFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?id=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Успешный поиск по имени")
    public void findByNameSuccess() throws Exception {
        Customer customer = new Customer();
        customer.setName("Alice");
        Customer savedCustomer = customerRepository.save(customer);
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?name=" + savedCustomer.getName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[{\"id\":" + savedCustomer.getId()
                                + ",\"name\":\"" + savedCustomer.getName() + "\"}]",
                        true));
    }

    @Test
    @DisplayName("Ошибка поиска по имени")
    public void findByNameFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?name=test"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        "[]",
                        true));
    }

}

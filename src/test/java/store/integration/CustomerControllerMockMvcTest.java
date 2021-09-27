package store.integration;

import store.dto.CustomerDto;
import store.model.Customer;
import store.repository.CustomerRepository;
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

    @Test
    @DisplayName("Успешное создание объекта")
    public void createSuccess() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("test");

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDto))
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
}
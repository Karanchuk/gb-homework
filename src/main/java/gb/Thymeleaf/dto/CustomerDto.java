package gb.Thymeleaf.dto;

import gb.Thymeleaf.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Integer id;
    private String name;

    public static CustomerDto valueOf(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName()
        );
    }

    public Customer mapToCustomer() {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        return customer;
    }
}

package gb.SpringRest_1.dto;

import gb.SpringRest_1.model.Customer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель покупателя", value = "CustomerDto")
public class CustomerDto {
    @ApiModelProperty(
            value    = "id",
            dataType = "Integer",
            example = "1"
    )
    private Integer id;
    @ApiModelProperty(
            value    = "name",
            dataType = "String",
            example = "Paul",
            required = true
    )
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

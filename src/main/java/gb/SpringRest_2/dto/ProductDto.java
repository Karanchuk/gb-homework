package gb.SpringRest_2.dto;

import gb.SpringRest_2.model.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Product model", value = "ProductDto")
public class ProductDto {
    @ApiModelProperty(
            value    = "id",
            dataType = "Integer",
            example = "1"
    )
    private Integer id;
    @ApiModelProperty(
            value    = "title",
            dataType = "String",
            example = "Potato",
            required = true
    )
    private String title;
    @ApiModelProperty(
            value    = "cost",
            dataType = "Integer",
            example = "200"
    )
    private int cost;

    public static ProductDto valueOf(Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getCost()
        );
    }

    public Product mapToProduct() {
        Product product = new Product();
        product.setId(id);
        product.setCost(cost);
        product.setTitle(title);
        return product;
    }
}

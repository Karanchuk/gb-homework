package gb.Spring.model;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String title;
    private int cost;

    @Company
    private String manufacturer;

}

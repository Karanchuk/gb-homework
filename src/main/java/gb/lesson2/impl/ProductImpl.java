package gb.lesson2.impl;

import gb.lesson2.Product;
import gb.lesson2.ProductRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("product")
@Scope("prototype")
public class ProductImpl implements Product {

    private Integer id;
    private String name;
    private Long price;

    @Override
    public void init(ProductRepository productRepository, String name, Long price) {
        id = productRepository.getMaxID() + 1;
        this.name = name;
        this.price = price;
        productRepository.addItem(this);
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getPrice() {
        return price;
    }

}

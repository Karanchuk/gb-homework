package gb.lesson2.impl;

import gb.lesson2.Product;
import gb.lesson2.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("productRepository")
public class ProductRepositoryImpl implements ProductRepository {

    private static ArrayList<Product> products = new ArrayList<>();

    @Override
    public Product getByID(Integer id) {
        for (Product element : products) {
            if (element.getID() == id)
                return element;
        }
        return null;
    }

    @Override
    public ArrayList<Product> getAll() {
        return products;
    }

    @Override
    public Integer getMaxID() {
        Integer maxID = 0;
        for (Product element : products) {
            if (element.getID() > maxID)
                maxID = element.getID();
        }
        return maxID;
    }

    @Override
    public Product findByName(String name) {
        for (Product element : products) {
            if (element.getName().toLowerCase().equals(name.toLowerCase()))
                return element;
        }
        return null;
    }

    @Override
    public void addItem(Product product) {
        products.add(product);
    }
}

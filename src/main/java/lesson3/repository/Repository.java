package lesson3.repository;

import lesson3.model.Product;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class Repository {
    private List<Product> list = new ArrayList<>();

    public Product getById(int id) {
        for (Product product : list) {
            if (product.getId() == id)
                return product;
        }
        return null;
    }

    public List<Product> getAll() {
        return list;
    }

    public void addNew(Product product) {
        list.add(product);
    }
}
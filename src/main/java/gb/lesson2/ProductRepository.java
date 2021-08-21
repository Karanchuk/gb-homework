package gb.lesson2;

import java.util.ArrayList;

public interface ProductRepository {

    Product getByID(Integer id);

    ArrayList<Product> getAll();

    Integer getMaxID();

    Product findByName(String name);

    void addItem(Product product);

}

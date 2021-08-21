package gb.lesson2.impl;

import gb.lesson2.Cart;
import gb.lesson2.Product;
import gb.lesson2.ProductRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("cart")
@Scope("prototype")
public class CartImpl implements Cart {

    private Map<Product, Integer> products = new ConcurrentHashMap<Product, Integer>();
    private ProductRepository productRepository;

    @Override
    public StringBuilder getList() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : products.entrySet()){
            Product product = entry.getKey();
            Integer value = entry.getValue();
            result.append("id: " + product.getID() + ", name: " + product.getName()
                    + ", price: " + product.getPrice() + ", value: " + value + System.lineSeparator());

        }
        return result;
    }
    
    @Override
    public void init(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(String name, Long price, Integer value) {

        Product product = productRepository.findByName(name);
        if (product == null) {
            product = new ProductImpl();
            product.init(productRepository, name, price);
        }

        Integer value2 = products.putIfAbsent(product, value);
        if (value2 != null)
            products.replace(product, value2 + value);
    }

    @Override
    public boolean removeProduct(Integer id, Integer value) {

        Product product = productRepository.getByID(id);
        Integer value2 = products.getOrDefault(product, -1);
        if (value2 == -1)
            return  false;
        else if (value2 - value > 0)
            products.replace(product, value2 - value);
        else
            products.remove(product);
        return true;

    }

    @Override
    public Integer getValue(String name) {
        Product product = productRepository.findByName(name);
        if (product != null)
            return products.getOrDefault(product, 0);
         else
            return 0;
    }

    @Override
    public StringBuilder getItem(String name) {
        StringBuilder result = new StringBuilder();
        Product product = productRepository.findByName(name);
        if (product != null) {
            Integer value = products.get(product);
            if (value != null) {
                return result.append("id: " + product.getID() + ", name: " + product.getName()
                        + ", price: " + product.getPrice() + ", value: " + value + System.lineSeparator());
            }
        }
        return result.append("There is no product in the list with a name " + name);
    }
}

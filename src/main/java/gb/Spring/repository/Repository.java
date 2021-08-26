package gb.Spring.repository;

import gb.Spring.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class Repository {
    private Map<Integer, Product> db = new ConcurrentHashMap<>();

    public Optional<Product> getById(Integer id) {
        return db.values().stream().filter(product -> (product.getId() == id)).findFirst();
    }

    public List<Product> getAll() {
        return db.values().stream().collect(Collectors.toUnmodifiableList());
    }

    public void addNew(Product product) {
        if (product.getId() == null)
            product.setId(db.size() + 1);
        db.put(product.getId(), product);
    }
}

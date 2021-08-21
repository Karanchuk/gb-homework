package gb.lesson2;

public interface Cart {

    void addProduct(String name, Long price, Integer value);

    boolean removeProduct(Integer id, Integer value);

    void init(ProductRepository productRepository);

    Integer getValue(String name);

    StringBuilder getList();

    StringBuilder getItem(String name);

}

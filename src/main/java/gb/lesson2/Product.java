package gb.lesson2;

public interface Product {
    Integer getID();

    String getName();

    Long getPrice();

    void init(ProductRepository productRepository, String name, Long price);

}

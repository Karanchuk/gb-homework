package gb.SpringRest_2.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "shopping",
            joinColumns = @JoinColumn(name = "productid"),
            inverseJoinColumns = @JoinColumn(name = "customerid")
    )
    private List<Customer> customers;

    @Column(name = "cost")
    private int cost;

    public String toString() {
        return String.format("Product [id = %d, title = %s, price = %d]", id, title, cost);
    }
}
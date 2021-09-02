package gb.Hibernate1.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "getById", query = "select s from Product s where s.id = :id")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private int cost;

    public String toString() {
        return String.format("Product [id = %d, title = %s, price = %d]", id, title, cost);
    }
}

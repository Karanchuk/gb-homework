package gb.Hibernate2.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "customers",
        indexes = {
                @Index(name = "name",columnList = "name")
        }
)
@NamedQueries({
        @NamedQuery(name = "getByID", query = "select s from Customer s where s.id = :id"),
        @NamedQuery(name = "getByName", query = "select s from Customer s where LOWER(s.name) = LOWER(:name)")
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "shopping",
            joinColumns = @JoinColumn(name = "customerid"),
            inverseJoinColumns = @JoinColumn(name = "productid")
    )
    private List<Product> products;

    public String toString() {
        return String.format("Customer [id = %d, name = %s]", id, name);
    }
}

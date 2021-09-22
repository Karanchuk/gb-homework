package gb.SpringRest_2.repository;

import gb.SpringRest_2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p from Product p where (:id is null OR :id = p.id) AND (:title is null OR :title = p.title) AND (:cost is null OR :cost = p.cost)")
    List<Product> findWithFilter(@Param("id") Optional<Integer> id, @Param("title") Optional<String> title, @Param("cost") Optional<Integer> cost);
}
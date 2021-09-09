package gb.SpringData.repository;

import gb.SpringData.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p from Product p " +
            "where (:title is null OR :title = p.title) " +
            "AND (:cost is null OR :cost = p.cost)")
    List<Product> findWithFilter(@Param("title") Optional<String> title, @Param("cost") Optional<Integer> cost);
}
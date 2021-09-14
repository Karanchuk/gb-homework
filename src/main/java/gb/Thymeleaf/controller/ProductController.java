package gb.Thymeleaf.controller;

import gb.Thymeleaf.dto.ProductDto;
import gb.Thymeleaf.model.Product;
import gb.Thymeleaf.service.ProductRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductRepositoryService productRepositoryService;

    @GetMapping
    public Object showAll(@RequestParam(value = "id") Optional<Integer> id,
                          @RequestParam(value = "title") Optional<String> title,
                          @RequestParam(value = "cost") Optional<Integer> cost) {

        if (id.isPresent() || title.isPresent() || cost.isPresent())
            return productRepositoryService.getWithFilter(id, title, cost);
        else
            return productRepositoryService.getAll();
    }

    @PostMapping
    public void create(@RequestBody ProductDto productDto) {
        productRepositoryService.addNew(productDto);
    }

    @PutMapping("/{id}/{cost}")
    public void updatePrice(@PathVariable(value = "id") int id, @PathVariable(value = "cost") int cost) {
        productRepositoryService.updatePrice(id, cost);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable(value = "id") int id) {
        productRepositoryService.removeById(id);
    }

}

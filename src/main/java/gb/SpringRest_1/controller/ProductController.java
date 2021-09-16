package gb.SpringRest_1.controller;

import gb.SpringRest_1.dto.ProductDto;
import gb.SpringRest_1.service.ProductRepositoryService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
@Api
public class ProductController {

    private final ProductRepositoryService productRepositoryService;

    @GetMapping
    @ApiOperation("Получение всех продуктов. Доступна фильтрация по полям id, title, cost.")
    public Object showAll(@ApiParam(name =  "id", type = "Integer", value = "product id", example = "1")
                            @RequestParam(value = "id") Optional<Integer> id,
                          @ApiParam(name =  "title", type = "String", value = "product title", example = "Orange")
                            @RequestParam(value = "title") Optional<String> title,
                          @ApiParam(name =  "cost", type = "Integer", value = "product cost", example = "100")
                            @RequestParam(value = "cost") Optional<Integer> cost) {

        if (id.isPresent() || title.isPresent() || cost.isPresent())
            return productRepositoryService.getWithFilter(id, title, cost);
        else
            return productRepositoryService.getAll();
    }

    @PostMapping
    @ApiOperation("Создание нового продукта")
    public void create(@ApiParam (name =  "productDto",
            type = "object",
            value = "product model",
            required = true)
                           @RequestBody ProductDto productDto) {
        productRepositoryService.addNew(productDto);
    }

    @PutMapping("/{id}/{cost}")
    @ApiOperation("Изменение цены продукта по id")
    public void updatePrice(@ApiParam(name =  "id", type = "Integer", value = "product id", example = "1", required = true)
                                @PathVariable(value = "id") int id,
                            @ApiParam(name =  "cost", type = "Integer", value = "product cost", example = "100", required = true)
                                @PathVariable(value = "cost") int cost) {
        productRepositoryService.updatePrice(id, cost);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление продукта по id")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(
            @ApiResponse(
                    code = 404,
                    message = "Product by id deleted"
            )
    )
    public void removeById(@ApiParam(name =  "id", type = "Integer", value = "product id", example = "1", required = true)
                               @PathVariable(value = "id") int id) {
        productRepositoryService.removeById(id);
    }

}

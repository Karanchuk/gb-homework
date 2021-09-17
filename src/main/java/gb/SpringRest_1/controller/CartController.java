package gb.SpringRest_1.controller;

import gb.SpringRest_1.service.CustomerRepositoryService;
import gb.SpringRest_1.service.ProductRepositoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/buy")
@Api
public class CartController {

    private final CustomerRepositoryService customerRepositoryService;
    private final ProductRepositoryService productRepositoryService;

    @ApiOperation("Adding purchased goods to the buyer")
    @GetMapping("/buyer={buyer}/product={product}")
    public void buy(@ApiParam(name =  "buyer", type = "Integer", value = "buyer id", example = "1", required = true)
                        @PathVariable(name = "buyer") int customer_id,
                    @ApiParam(name =  "product", type = "Integer", value = "product id", example = "1", required = true)
                        @PathVariable(name = "product") int product_id) {
        customerRepositoryService.newSale(customer_id, product_id);
    }

}

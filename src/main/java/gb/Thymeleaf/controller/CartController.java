package gb.Thymeleaf.controller;

import gb.Thymeleaf.dto.CustomerDto;
import gb.Thymeleaf.dto.ProductDto;
import gb.Thymeleaf.service.CustomerRepositoryService;
import gb.Thymeleaf.service.ProductRepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller()
@RequestMapping("/buy")
public class CartController {

    @Value("${services.url}")
    private String url;

    @Value("${services.port}")
    private String port;

    private final CustomerRepositoryService customerRepositoryService;
    private final ProductRepositoryService productRepositoryService;

    public CartController(CustomerRepositoryService customerRepositoryService,
                          ProductRepositoryService productRepositoryService) {
        this.customerRepositoryService = customerRepositoryService;
        this.productRepositoryService = productRepositoryService;
    }

    @GetMapping("/buyer={buyer}/product={product}")
    public String buy(Model model, @PathVariable(name = "buyer") int customer_id, @PathVariable(name = "product") int product_id) {
        Optional<CustomerDto> customer = customerRepositoryService.getByID(customer_id);
        Optional<ProductDto> product = productRepositoryService.getById(product_id);

        if (customer == null) {
            return "redirect:/customer";
        }

        if (product == null) {
            return "redirect:/product";
        }

        customerRepositoryService.newSale(customer.get(), product.get());
        return "redirect:/customers";
    }

}

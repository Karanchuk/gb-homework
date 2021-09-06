package gb.Hibernate2.controller;

import gb.Hibernate2.model.Product;
import gb.Hibernate2.service.ProductRepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    @Value("${services.url}")
    private String url;

    @Value("${server.port}")
    private String port;


    private final ProductRepositoryService productRepositoryService;

    public ProductController(ProductRepositoryService productRepositoryService) {
        this.productRepositoryService = productRepositoryService;
    }

    @GetMapping("/product/{id}")
    public String findById(Model model, @PathVariable(value = "id") int id) {
        Product product = productRepositoryService.getById(id);
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("product", product);
        return (product == null) ? "redirect:/product" : "one-product";
    }

    @GetMapping("/product/delete/{id}")
    public String removeById(Model model, @PathVariable(value = "id") int id) {
        productRepositoryService.removeById(id);
        return "redirect:/product";
    }

    @GetMapping("/product/update/{id}/{cost}")
    public String updatePrice(Model model, @PathVariable(value = "id") int id, @PathVariable(value = "cost") int cost) {
        productRepositoryService.updatePrice(id, cost);
        return "redirect:/product";
    }

    @GetMapping("/product")
    public String showAll(Model model) {
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("products", productRepositoryService.getAll());
        return "products";
    }

    @GetMapping("/product/form")
    public String getForm(Model model) {
        Product product = new Product();
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("product", product);
        return "new-product";
    }

    @PostMapping("/product/form")
    public String create(Product product) {
        productRepositoryService.addNew(product);
        return "redirect:/product";
    }
}

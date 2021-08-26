package gb.Spring.controller;

import gb.Spring.model.CompanyAnnotationAnalyzer;
import gb.Spring.model.Product;
import gb.Spring.service.RepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Controller
public class ProductController {

    @Value("${services.url}")
    private String url;

    @Value("${server.port}")
    private String port;


    private final RepositoryService repositoryService;

    public ProductController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/product/{id}")
    public String findById(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/form");
        model.addAttribute("product", repositoryService.getById(id));
        return "one_product";
    }

    @GetMapping("/product")
    public String showAll(Model model) {
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/form");
        model.addAttribute("products", repositoryService.getAll());
        return "products";
    }

    @GetMapping("/form")
    public String getForm(Model model) {
        Product product = new Product();
        product.setManufacturer("Prod"); // test
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/form");
        model.addAttribute("product", product);
        return "new-product";
    }

    @PostMapping("/form")
    public String create(Product product) {
        repositoryService.addNew(product);
        return "redirect:/product";
    }


}

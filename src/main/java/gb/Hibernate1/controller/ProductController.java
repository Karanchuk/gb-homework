package gb.Hibernate1.controller;

import gb.Hibernate1.model.Product;
import gb.Hibernate1.service.RepositoryService;
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


    private final RepositoryService repositoryService;

    public ProductController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/product/{id}")
    public String findById(Model model, @PathVariable(value = "id") int id) {
        Product product = repositoryService.getById(id);
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/form");
        model.addAttribute("product", product);
        return (product == null) ? "redirect:/product" : "one-product";
    }

    @GetMapping("/product/delete/{id}")
    public String removeById(Model model, @PathVariable(value = "id") int id) {
        repositoryService.removeById(id);
        return "redirect:/product";
    }

    @GetMapping("/product/update/{id}/{cost}")
    public String updatePrice(Model model, @PathVariable(value = "id") int id, @PathVariable(value = "cost") int cost) {
        repositoryService.updatePrice(id, cost);
        return "redirect:/product";
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

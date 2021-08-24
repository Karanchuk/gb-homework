package lesson3.controller;

import lesson3.service.RepositoryService;
import lesson3.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class ProductController {

    private final RepositoryService repositoryService;

    public ProductController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String findById(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("product", repositoryService.getById(id));
        return "one_product";
    }

    @GetMapping("/product")
    public String showAll(Model model) {
        model.addAttribute("products", repositoryService.getAll());
        return "products";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "new_product";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String create(Product product) {
        repositoryService.addNew(product);
        return "redirect:/product";
    }

//    @PostMapping
//    public String save(@ModelAttribute("person") Product product) {
//         repositoryService.addNew(product);
//        return "redirect:/product";
//    }

}

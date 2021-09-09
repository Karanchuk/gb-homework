package gb.SpringData.controller;

import gb.SpringData.model.Customer;
import gb.SpringData.model.Product;
import gb.SpringData.service.CartRepositoryService;
import gb.SpringData.service.CustomerRepositoryService;
import gb.SpringData.service.ProductRepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
    @Value("${services.url}")
    private String url;

    @Value("${services.port}")
    private String port;

    private final CustomerRepositoryService customerRepositoryService;
    private final ProductRepositoryService productRepositoryService;
    private final CartRepositoryService cartRepositoryService;

    public CustomerController(CustomerRepositoryService customerRepositoryService,
                              ProductRepositoryService productRepositoryService,
                              CartRepositoryService cartRepositoryService) {
        this.customerRepositoryService = customerRepositoryService;
        this.productRepositoryService = productRepositoryService;
        this.cartRepositoryService = cartRepositoryService;
    }

    @GetMapping("/customer/{id}")
    public String findById(Model model, @PathVariable(value = "id") int id) {
        Optional<Customer> customer = customerRepositoryService.getByID(id);
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("customer", customer);
        return (customer == null) ? "redirect:/customer" : "one-customer";
    }

    @GetMapping("/customer/name={name}")
    public String findByName(Model model, @PathVariable(value = "name") String name) {
        List<Customer> customers = customerRepositoryService.getByName(name);
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("customers", customers);
        return (customers.isEmpty()) ? "redirect:/customer" : "customers";
    }

    @GetMapping("/customer")
    public String showAll(Model model) {
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("customers", customerRepositoryService.getAll());
        return "customers";
    }

    @GetMapping("/customer/form")
    public String getForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("customer", customer);
        return "new-customer";
    }

    @PostMapping("/customer/form")
    public String create(Customer customer) {
        customerRepositoryService.addNew(customer);
        return "redirect:/customer";
    }

    @GetMapping("/buy/buyer={buyer}/product={product}")
    public String buy(Model model, @PathVariable(name = "buyer") int customer_id, @PathVariable(name = "product") int product_id) {
        //Integer product_id = 1;
        Optional<Customer> customer = customerRepositoryService.getByID(customer_id);
        Optional<Product> product = productRepositoryService.getById(product_id);

        if (customer == null) {
            return "redirect:/customer";
        }

        if (product == null) {
            return "redirect:/product";
        }

        cartRepositoryService.newSale(customer.get(), product.get());
        return "redirect:/customers";
    }

}

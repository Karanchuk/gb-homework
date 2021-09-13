package gb.Thymeleaf.controller;

import gb.Thymeleaf.dto.CustomerDto;
import gb.Thymeleaf.model.Customer;
import gb.Thymeleaf.service.CustomerRepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Value("${services.url}")
    private String url;

    @Value("${services.port}")
    private String port;

    private final CustomerRepositoryService customerRepositoryService;

    public CustomerController(CustomerRepositoryService customerRepositoryService) {
        this.customerRepositoryService = customerRepositoryService;
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable(value = "id") int id) {
        Optional<CustomerDto> customer = customerRepositoryService.getByID(id);
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("customer", customer);
        return (customer == null) ? "redirect:/customer" : "one-customer";
    }

    @GetMapping("/name={name}")
    public String findByName(Model model, @PathVariable(value = "name") String name) {
        List<CustomerDto> customers = customerRepositoryService.getByName(name);
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("customers", customers);
        return (customers.isEmpty()) ? "redirect:/customer" : "customers";
    }

    @GetMapping()
    public List<CustomerDto> showAll(Model model) {
//        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
//        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
//        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
//        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
//        model.addAttribute("Сustomers", customerRepositoryService.getAll());
        return customerRepositoryService.getAll();//"index";
    }

    @GetMapping("/form")
    public String getForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("productPage", "http://" + url + ":" + port + "/product");
        model.addAttribute("formPage", "http://" + url + ":" + port + "/product/form");
        model.addAttribute("customerPage", "http://" + url + ":" + port + "/customer");
        model.addAttribute("addCustomerPage", "http://" + url + ":" + port + "/customer/form");
        model.addAttribute("customer", customer);
        return "new-customer";
    }

    @PostMapping("/form")
    public String create(Customer customer) {
        customerRepositoryService.addNew(customer);
        return "redirect:/customer";
    }

}

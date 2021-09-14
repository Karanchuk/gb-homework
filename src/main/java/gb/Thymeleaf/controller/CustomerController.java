package gb.Thymeleaf.controller;

import gb.Thymeleaf.dto.CustomerDto;
import gb.Thymeleaf.service.CustomerRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerRepositoryService customerRepositoryService;

    @GetMapping()
    public Object showAll(@RequestParam(value = "id") Optional<Integer> id, @RequestParam(value = "name") Optional<String> name) {
        if (id.isPresent())
            return customerRepositoryService.getByID(id.get());
        else if (name.isPresent())
            return customerRepositoryService.getByName(name.get());
        else
            return customerRepositoryService.getAll();
    }

    @PostMapping
    public void save(@RequestBody CustomerDto customerDto) {
        customerRepositoryService.addNew(customerDto);
    }

    @PutMapping("/{id}/{name}")
    public void update(@PathVariable(value = "id") int id, @PathVariable(value = "name") String name) {
        customerRepositoryService.update(id, name);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable(value = "id") int id) {
        customerRepositoryService.removeById(id);
    }
}

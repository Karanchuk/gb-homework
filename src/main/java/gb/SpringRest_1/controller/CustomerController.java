package gb.SpringRest_1.controller;

import gb.SpringRest_1.dto.CustomerDto;
import gb.SpringRest_1.service.CustomerRepositoryService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Api
public class CustomerController {

    private final CustomerRepositoryService customerRepositoryService;

    @GetMapping
    @ApiOperation("Получение всех покупателей. Доступна фильтрация по полям id, name.")
    public Object showAll(@ApiParam(name =  "id", type = "Integer", value = "customer id", example = "1")
                            @RequestParam(value = "id") Optional<Integer> id,
                          @ApiParam(name =  "name", type = "String", value = "customer name", example = "Anna")
                            @RequestParam(value = "name") Optional<String> name) {
        if (id.isPresent())
            return customerRepositoryService.getByID(id.get());
        else if (name.isPresent() && !(name.get().equals("undefined") || name.get().equals("")))
            return customerRepositoryService.getByName(name.get());
        else
            return customerRepositoryService.getAll();
    }

    @GetMapping("/sort")
    @ApiOperation("Получение всех пользователей с сортировкой имени в алфавитном порядке")
    public List<CustomerDto> showWithSort() {
        return customerRepositoryService.getAll().stream()
                .sorted(Comparator.comparing(CustomerDto::getName))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Создание нового покупателя")
    public void save(@ApiParam (name =  "customerDto",
            type = "object",
            value = "customer model",
            required = true)
                         @RequestBody CustomerDto customerDto) {
        customerRepositoryService.addNew(customerDto);
    }

    @PutMapping("/{id}/{name}")
    @ApiOperation("Изменение имени покупателя по id")
    public void update(@ApiParam(name =  "id", type = "Integer", value = "customer id", example = "1", required = true)
                        @PathVariable(value = "id") int id,
                       @ApiParam(name =  "name", type = "String", value = "new customer name", example = "Derrick", required = true)
                        @PathVariable(value = "name") String name) {
        customerRepositoryService.update(id, name);
    }

    @ApiOperation("Удаление покупателя по id")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(
            @ApiResponse(
                    code = 404,
                    message = "Customer by id deleted"
            )
    )
    @DeleteMapping("/{id}")
    public void removeById(@ApiParam(name =  "id", type = "Integer", value = "customer id", example = "1", required = true)
                               @PathVariable(value = "id") int id) {
        customerRepositoryService.removeById(id);
    }
}

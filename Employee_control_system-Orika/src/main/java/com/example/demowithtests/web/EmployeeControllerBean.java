package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.orika.EmployeeConverter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;




@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeController {

    private final Service service;


    private final EmployeeConverter employeeConverter;

    //Операция сохранения юзера в базу данных

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        System.out.println(employeeDto);
        var entity = employeeConverter.getMapperFacade().map(employeeDto, Employee.class);
        var dto = employeeConverter.toDto(service.create(entity));
        System.out.println(dto);
        return dto;
    }


    //Получение списка юзеров
//    @GetMapping("/users")
//    @ResponseStatus(HttpStatus.OK)
//    public List<EmployeeReadDto> getAllUsers() {
//        List<Employee> employees = service.getAll();
//        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
//        for (Employee employee : employees) {
//            employeesReadDto.add(
//                    entityMapper.employeeToEmployeeReadDto(employee)
//            );
//        }
//        return employeesReadDto;
//    }

    //Получения юзера по id
    @Override
    @GetMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        var employee = service.getById(id);
        return employeeConverter.toReadDto(employee);
    }


//    //Обновление юзера
//    @SneakyThrows
//    @PutMapping("/users/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public EmployeeReadDto refreshEmployee(@PathVariable("id") String id, @RequestBody EmployeeDto employeeDto) {
//        Integer parseId = Integer.parseInt(id);
//        return entityMapper.employeeToEmployeeReadDto(
//                service.updateById(parseId, entityMapper.employeeDtoToEmployee(employeeDto)
//                )
//        );
//    }

    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable String id) {
        Integer parseId = Integer.parseInt(id);
        service.removeById(parseId);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        service.removeAll();
    }


    //@PatchMapping("/replaceNull")
    @GetMapping("/replaceNull")
    @ResponseStatus(HttpStatus.OK)
    public void replaceNull() {
        service.processor();
    }

    @PostMapping("/sendEmailByCountry")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailByCountry(@RequestParam String country, @RequestParam String text) {
        service.sendEmailByCountry(country, text);
    }

    @PostMapping("/sendEmailByCity")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailByCity(@RequestParam String city, @RequestParam String text) {
        service.sendEmailByCountry(city, text);
    }

    @GetMapping("/metricsForEmployee")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> metrics(@RequestParam String country){
        return service.metricsForEmployee(country);
    }

}

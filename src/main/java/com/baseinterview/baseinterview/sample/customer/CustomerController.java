package com.baseinterview.baseinterview.sample.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("sample/customer")
@Slf4j
public class CustomerController {
    private final CustomerRepository repository;

    @Autowired
    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Customer create(@RequestBody @Valid Customer input) {
        return repository.save(input);
    }

    @GetMapping
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Customer> findById(@PathVariable("id") Long id) {
        return repository.findById(id);
    }

    @GetMapping("byParam")
    public Optional<Customer> findByName(@RequestParam("name") String name) {
        return repository.findByName(name);
    }

    @GetMapping("pageable")
    public Page<Customer> findAdultsPageable(Pageable pageable) {
        log.debug(pageable.toString());
        return repository.findByAgeIsGreaterThanEqual(18L, pageable);
    }

    @GetMapping("nativePageable")
    public Page<Customer> findByNativePageable(Pageable pageable) {
        return repository.findByNativeWithPagination(18L, pageable);
    }
}

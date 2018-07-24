package com.baseinterview.baseinterview.sample.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("sample/customer")
public class CustomerController {
    private final CustomerRepository repository;

    @Autowired
    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Customer create(@RequestBody @Valid Customer input){
        return repository.save(input);
    }

    @GetMapping
    public List<Customer> findAll(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Customer> findById(@PathVariable("id") Long id){
        return repository.findById(id);
    }
}

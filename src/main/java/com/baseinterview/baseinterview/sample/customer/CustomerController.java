package com.baseinterview.baseinterview.sample.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sample/customer")
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

    @GetMapping("/rank/{words}")
    public List<CustomerRank> rank(@PathVariable("words") String words) {
        return repository.rank(words);
    }

    @GetMapping
    public List<Customer> findAll(){
        return repository.findAll();
    }

}

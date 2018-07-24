package com.baseinterview.baseinterview.sample.customer;

import com.baseinterview.baseinterview.sample.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("sample/customer")
public class CustomerController {
    private final CustomerJpaRepository jpaRepository;
    private final CustomerEsRepository esRepository;

    @Autowired
    public CustomerController(CustomerJpaRepository jpaRepository,
                              CustomerEsRepository esRepository) {
        this.jpaRepository = jpaRepository;
        this.esRepository = esRepository;
    }

    @PostMapping
    public Customer create(@RequestBody @Valid Customer input){
        Customer saved = jpaRepository.save(input);
        esRepository.save(saved);
        return saved;
    }

    @GetMapping
    public List<Customer> findAll(){
        return jpaRepository.findAll();
    }

    @PostMapping("/search")
    public List<CustomerSearchResult> search(@RequestBody UserQuery query){
        return esRepository.search(query);
    }
}

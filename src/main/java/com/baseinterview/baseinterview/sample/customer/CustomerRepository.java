package com.baseinterview.baseinterview.sample.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, PagingAndSortingRepository<Customer, Long> {

    Optional<Customer> findByName(String name);

    Page<Customer> findByAgeIsGreaterThanEqual(Long age, Pageable pageable);
}

package com.baseinterview.baseinterview.sample.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select  id,  name,  age,  ts_rank_cd(text, query) rank from   customer, to_tsquery('english', '?1') query, to_tsvector(name || ' ' || age) text  order by rank DESC",
    name = "rank", nativeQuery = true)
    public List<CustomerRank> rank(String words);
}

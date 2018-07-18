package com.baseinterview.baseinterview.sample.customer;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private Long age;
}

package com.baseinterview.baseinterview.sample.customer;

import lombok.Data;

@Data
public class CustomerRank {
    public String id;
    public String name;
    public long age;
    public double rank;

    public CustomerRank(String id, String name, long age, double rank) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.rank = rank;
    }
}

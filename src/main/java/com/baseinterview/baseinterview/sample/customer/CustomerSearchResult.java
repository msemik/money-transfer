package com.baseinterview.baseinterview.sample.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerSearchResult {
    private Long id;
    private String name;
    private Long age;
}

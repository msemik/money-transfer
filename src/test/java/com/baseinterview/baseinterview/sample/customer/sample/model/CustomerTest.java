package com.baseinterview.baseinterview.sample.customer.sample.model;

import com.baseinterview.baseinterview.sample.customer.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTest {


    @Test
    public void lombokGettersAndSetters() {
        //given
        Customer customer = Customer.builder().build();

        //when
        customer.setId(1L);

        //then
        Long id = customer.getId();
        assertThat(id).isEqualTo(1L);
    }

    @Test
    public void lombokBuilder() {
        //given
        Customer.CustomerBuilder builder = Customer.builder();

        //when
        Customer customer = builder.id(1L).build();

        //then
        assertThat(customer.getId()).isEqualTo(1L);
    }

    @Test
    public void mockito() {
        //given
        Customer mock = mock(Customer.class);
        when(mock.getId()).thenReturn(2L);

        //when
        Long id = mock.getId();

        //then
        assertThat(id).isEqualTo(2L);
    }
}
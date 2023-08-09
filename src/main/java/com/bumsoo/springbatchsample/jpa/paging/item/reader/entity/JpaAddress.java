package com.bumsoo.springbatchsample.jpa.paging.item.reader.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class JpaAddress {

    @Id
    @GeneratedValue
    private Long id;
    private String location;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private JpaCustomer jpaCustomer;

}

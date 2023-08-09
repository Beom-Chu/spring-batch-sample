package com.bumsoo.springbatchsample.jpa.paging.item.reader.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class JpaCustomer {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @OneToOne(mappedBy = "jpaCustomer")
    private JpaAddress jpaAddress;

}

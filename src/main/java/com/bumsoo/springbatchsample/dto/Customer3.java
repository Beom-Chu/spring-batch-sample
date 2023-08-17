package com.bumsoo.springbatchsample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "customer3")
@AllArgsConstructor
@NoArgsConstructor
public class Customer3 {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
}

package com.bumsoo.springbatchsample.jdbc.cursor.item.reader;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String brithDate;
}

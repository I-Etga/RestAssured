package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.lang.GString;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = "id", allowSetters = true)
public class Spartan {
    private int id;
    private String name;
    private String gender;
    private long phone;

}

package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = "id", allowSetters = true)
public class Product {
    /*
      {
            "id": 17,
            "name": "Pineapple-Slice",
            "self_link": "/shop/v2/products/17"
        },
     */

    private int id;
    private String name;
    private String self_link;
}

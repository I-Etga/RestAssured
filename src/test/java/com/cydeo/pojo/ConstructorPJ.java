package com.cydeo.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConstructorPJ {
    @JsonProperty("constructorId")
    private String constructorId;

    @JsonProperty("name")
    private String name;
}

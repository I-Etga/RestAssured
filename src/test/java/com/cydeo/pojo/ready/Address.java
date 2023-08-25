package com.cydeo.pojo.ready;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "addressId",
    "street",
    "city",
    "state",
    "zipCode"
})
@Data
public class Address {

    @JsonProperty("addressId")
    public Integer addressId;
    @JsonProperty("street")
    public String street;
    @JsonProperty("city")
    public String city;
    @JsonProperty("state")
    public String state;
    @JsonProperty("zipCode")
    public Integer zipCode;

}
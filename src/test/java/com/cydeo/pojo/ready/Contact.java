package com.cydeo.pojo.ready;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "contactId",
    "phone",
    "emailAddress",
    "permanentAddress"
})
@Data
public class Contact {

    @JsonProperty("contactId")
    public Integer contactId;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("emailAddress")
    public String emailAddress;
    @JsonProperty("permanentAddress")
    public String permanentAddress;

}
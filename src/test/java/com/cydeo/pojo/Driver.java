package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

@JsonIgnoreProperties(ignoreUnknown = true)
public class Driver {
    @JsonProperty("driverId")
    private String driverId;

    @JsonProperty("givenName")
    private String givenName;

    @JsonProperty("familyName")
    private String familyName;

    @JsonProperty("nationality")
    private String nationality;
}

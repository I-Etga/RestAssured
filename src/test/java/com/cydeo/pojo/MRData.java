package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

//@Getter@Setter@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MRData {

    private String limit;
    private String total;


    @JsonProperty("StatusTable")
    private StatusTable statusTable;



}
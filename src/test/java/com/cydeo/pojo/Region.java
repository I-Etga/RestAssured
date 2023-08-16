package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString

public class Region {

    @JsonProperty("region_id")  //hey Jackson, find region_id from JSON response and set value to variable below
    private int region_id;

    @JsonProperty("region_name")
    private String region_name;

    private List<Link> links;

}



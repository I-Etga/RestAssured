package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StatusTable {

    @JsonProperty("Status")
    private List<Status> statusList;
}
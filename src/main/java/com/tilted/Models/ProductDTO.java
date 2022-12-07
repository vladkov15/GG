package com.tilted.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class ProductDTO {
    @JsonProperty("id")
    public int Id;
    @JsonProperty("name")
    public String Name;
    @Range(min = 1, max = 1000)
    @JsonProperty("amount")
    public int Amount;
    @JsonProperty("date_of_create")
    public String DateOfCreate;
    @JsonProperty("expiration_date")
    public String ExpirationDate;
    @JsonProperty("description")
    public String Description;
    @JsonProperty
    public String Defect;
}

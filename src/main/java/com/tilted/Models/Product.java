package com.tilted.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int Id;
    public String Name;
    public int Amount;
    public String DateOfCreate;
    public String ExpirationDate;
    public String Description;
    public String Defect;

}

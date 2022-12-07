package com.tilted.Mappers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tilted.Models.Product;
import com.tilted.Models.ProductDTO;


import java.lang.reflect.Type;
import java.util.ArrayList;

public final class ProductMapper {
    public static ProductDTO ToDTO(Product product) {
        var productDto = new ProductDTO();
        productDto.Id = product.getId();
        productDto.Name = product.getName();
        productDto.Amount = product.getAmount();
        productDto.DateOfCreate = product.getDateOfCreate();
        productDto.ExpirationDate = product.getExpirationDate();
        productDto.Description = product.getDescription();
        productDto.Defect = product.getDefect();
        return productDto;
    }

    public static Product ToModel(ProductDTO productDTO) {
        var product = new Product();
        product.setId(productDTO.Id);
        product.setName(productDTO.Name);
        product.setAmount(productDTO.Amount);
        product.setDateOfCreate(productDTO.DateOfCreate);
        product.setExpirationDate(productDTO.ExpirationDate);
        product.setDescription(productDTO.Description);
        product.setDefect(productDTO.Defect);
        return product;
    }
}

package com.tilted.Interfaces;

import com.tilted.Models.ProductDTO;


import java.util.List;

public interface IProductsService {
    ProductDTO Create(ProductDTO product);
    List<ProductDTO> GetAll();
    ProductDTO GetById(int id);
    void Update(ProductDTO user);
    boolean DeleteById(int id);
}

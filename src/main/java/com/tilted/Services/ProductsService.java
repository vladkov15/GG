package com.tilted.Services;

import com.tilted.Interfaces.IProductsService;
import com.tilted.Mappers.ProductMapper;
import com.tilted.Models.ProductDTO;
import com.tilted.Repositories.IProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService implements IProductsService {

    private final IProductsRepository productsRepository;

    public ProductsService(IProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    @Override
    public ProductDTO Create(ProductDTO product) {
        return ProductMapper.ToDTO(productsRepository.save(ProductMapper.ToModel(product)));
    }

    @Override
    public List<ProductDTO> GetAll() {
        return productsRepository.findAll().stream().map(ProductMapper::ToDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO GetById(int id) {
        return productsRepository.findById(id).map(ProductMapper::ToDTO).orElse(null);
    }

    @Override
    public void Update(ProductDTO product) {
        productsRepository.save(ProductMapper.ToModel(product));
    }

    @Override
    public boolean DeleteById(int id) {
        if (!productsRepository.existsById(id)) {
            return false;
        }

        productsRepository.deleteById(id);
        return true;
    }
    }


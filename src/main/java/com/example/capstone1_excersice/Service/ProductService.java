package com.example.capstone1_excersice.Service;

import com.example.capstone1_excersice.Model.Product;
import com.example.capstone1_excersice.Repository.CategoryRepository;
import com.example.capstone1_excersice.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Integer id ){
        for(Product product : getAllProducts()){
            if (product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }

    public Boolean addProduct(Product product){

        if(!categoryRepository.existsById(product.getCategoryId())) return false;

        productRepository.save(product);
        return true;
    }

    public String updateProduct(Integer id, Product product){

        if(!categoryRepository.existsById(product.getCategoryId())) return "Error: category not found";

        if(productRepository.existsById(id)){
            product.setId(id);
            productRepository.save(product);
            return "success";
        }

        return "Error: product not found";
    }

    public Boolean deleteProduct(Integer id){

        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

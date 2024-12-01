package com.example.capstone1_excersice.Service;

import com.example.capstone1_excersice.Model.Category;
import com.example.capstone1_excersice.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id ){
        for(Category category : getAllCategories()){
            if (category.getId().equals(id)){
                return category;
            }
        }
        return null;
    }

    public void addCategory(Category category){

        categoryRepository.save(category);
    }

    public Boolean updateCategory(Integer id , Category category){

        if(categoryRepository.existsById(id)){
            category.setId(id);
            categoryRepository.save(category);
            return true;
        }

        return false;
    }

    public Boolean deleteCategory(Integer id ){

        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return true;
        }

        return false;
    }
}

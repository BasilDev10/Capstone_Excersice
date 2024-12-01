package com.example.capstone1_excersice.Controller;

import com.example.capstone1_excersice.ApiResponse.ApiResponse;
import com.example.capstone1_excersice.Model.Category;
import com.example.capstone1_excersice.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/get")
    public ResponseEntity getCategory(){

        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        categoryService.addCategory(category);
        return ResponseEntity.status(201).body(new ApiResponse("Category is added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable Integer id, @RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if (categoryService.updateCategory(id,category)) return ResponseEntity.ok(new ApiResponse("category is updated"));
        else return ResponseEntity.status(400).body(new ApiResponse("category not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id){

        if (categoryService.deleteCategory(id)) return ResponseEntity.ok(new ApiResponse("category is delete"));
        else return ResponseEntity.status(400).body(new ApiResponse("category not found"));
    }
}

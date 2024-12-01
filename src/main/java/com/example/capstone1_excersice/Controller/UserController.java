package com.example.capstone1_excersice.Controller;

import com.example.capstone1_excersice.ApiResponse.ApiResponse;
import com.example.capstone1_excersice.Model.User;
import com.example.capstone1_excersice.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        userService.addUser(user);
        return ResponseEntity.status(201).body(new ApiResponse("User is added"));

    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id , @RequestBody @Valid User user , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if(userService.updateUser(id,user)) return ResponseEntity.ok(new ApiResponse("user is updated"));
        else return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id ){

        if(userService.deleteUser(id)) return ResponseEntity.ok(new ApiResponse("user is deleted"));
        else return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }


}

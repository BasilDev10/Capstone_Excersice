package com.example.capstone1_excersice.Service;

import com.example.capstone1_excersice.Model.User;
import com.example.capstone1_excersice.Repository.TransferRequestRepository;
import com.example.capstone1_excersice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final TransferRequestRepository transferRequestRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public User getUserById(Integer id ){
        for(User user : getAllUsers()){
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public void addUser(User user){

        userRepository.save(user);
    }
    public Boolean updateUser(Integer id , User user){

        if (userRepository.existsById(id)){
            user.setId(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public boolean deleteUser(Integer id ){

        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

package com.example.capstone1_excersice.Service;

import com.example.capstone1_excersice.Model.TransferRequest;
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
public class TransferRequestService {

    private final TransferRequestRepository transferRequestRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<TransferRequest> getAllTransferRequests(){
        return transferRequestRepository.findAll();
    }

    public TransferRequest getByTransferRequestId(Integer userId){
        for(TransferRequest transferRequest : getAllTransferRequests()){
            if (transferRequest.getUserIdFrom().equals(userId)){
                return transferRequest;
            }
        }
        return null;
    }
    public String addTransferRequest(TransferRequest transferRequest){
        if(userService.getUserById(transferRequest.getUserIdFrom()) == null) return "Error: userIdFrom not found";
        if(userService.getUserById(transferRequest.getUserIdTo()) == null) return "Error: userIdTo not found";
        transferRequestRepository.save(transferRequest);
        return "success";
    }

    public String updateTransferRequest(Integer id, TransferRequest transferRequest){
        if(userService.getUserById(transferRequest.getUserIdFrom())== null) return "Error: userIdFrom not found";
        if(userService.getUserById(transferRequest.getUserIdTo())== null) return "Error: userIdTo not found";
        if(transferRequestRepository.existsById(id)){
            transferRequest.setId(id);
            transferRequestRepository.save(transferRequest);
            return "success";
        }
        return "Error: transferRequest not found";
    }

    public Boolean deleteTransferRequest(Integer id){
        if(transferRequestRepository.existsById(id)){
            transferRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }



    public String updateRequestStatus(Integer adminId , Integer userIdFrom,String status, Integer requestId){

        User userFrom = userService.getUserById(userIdFrom);
        TransferRequest transferRequest = getByTransferRequestId(requestId);

        if (!status.equalsIgnoreCase("approved") && !status.equalsIgnoreCase("rejected")) return "Error: status must be approved or rejected";

        if(userFrom == null) return "Error: userIdFrom not found";

        if(transferRequest == null) return "Error: requestId not found";

        if(status.equalsIgnoreCase("approved") && transferRequest.getStatus().equalsIgnoreCase("pending")){
            transferRequest.setStatus(status);
            transferRequestRepository.save(transferRequest);
            return transferBalance(adminId,userIdFrom, transferRequest.getUserIdTo(), transferRequest.getAmount());
        }else if(transferRequest.getStatus().equalsIgnoreCase("rejected")){
            return "Error: request is already rejected";
        }else if(transferRequest.getStatus().equalsIgnoreCase("approved")){
            return "Error: request is already approved";
        } else{
            transferRequest.setStatus(status);
            transferRequestRepository.save(transferRequest);
            return "success";
        }


    }

    public String transferBalance(Integer adminId , Integer userIdFrom,Integer userIdTo, double amount){
        User admin = userService.getUserById(adminId);
        User userFrom = userService.getUserById(userIdFrom);
        User userTo = userService.getUserById(userIdTo);
        if(admin == null) return "Error:Admin not found";
        if(!admin.getRole().equalsIgnoreCase("admin")) return "Error: user not admin";
        if(userFrom == null || userTo == null) return "Error:User not found";
        if(userFrom.getBalance() < amount) return "Error: user balance is not enough";
        userFrom.setBalance(userFrom.getBalance() - amount);
        userService.updateUser(userIdFrom, userFrom);
        userTo.setBalance(userTo.getBalance() + amount);
        userService.updateUser(userIdTo, userTo);
        return "success";
    }
}

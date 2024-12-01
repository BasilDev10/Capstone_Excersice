package com.example.capstone1_excersice.Controller;

import com.example.capstone1_excersice.ApiResponse.ApiResponse;
import com.example.capstone1_excersice.Model.TransferRequest;
import com.example.capstone1_excersice.Service.TransferRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfer-request")
@RequiredArgsConstructor
public class TransferRequestController {

    private final TransferRequestService transferRequestService;

    @GetMapping("/get")
    public ResponseEntity getTransferRequest(){
        return ResponseEntity.ok(transferRequestService.getAllTransferRequests());
    }

    @PostMapping("/add")
    public ResponseEntity addTransferRequest(@RequestBody @Valid TransferRequest transferRequest , Errors errors){

        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String result = transferRequestService.addTransferRequest(transferRequest);
        if (result.equalsIgnoreCase("success")) return ResponseEntity.ok(new ApiResponse("Transfer request is added"));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTransferRequest(@PathVariable Integer id, @RequestBody @Valid TransferRequest transferRequest ,Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String result = transferRequestService.updateTransferRequest(id,transferRequest);
        if (result.equalsIgnoreCase("success")) return ResponseEntity.ok(new ApiResponse("Transfer request is updated"));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTransferRequest(@PathVariable Integer id){
        if(transferRequestService.deleteTransferRequest(id)) return ResponseEntity.ok(new ApiResponse("Transfer request is deleted"));
        else return ResponseEntity.status(400).body(new ApiResponse("Transfer request not found"));
    }

    // Extra endpoint 6
    //This endpoint allows an admin to update the status of a transfer request made by a user. The request can either be approved or rejected based on the admin's action.
    @PutMapping("/update-request-status/{adminId}/{userIdFrom}/{status}/{requestId}")
    public ResponseEntity updateRequestStatus(@PathVariable Integer adminId ,@PathVariable Integer userIdFrom,@PathVariable String status,@PathVariable Integer requestId){
        String result = transferRequestService.updateRequestStatus(adminId,userIdFrom,status,requestId);
        if (result.equalsIgnoreCase("success")) return ResponseEntity.ok(new ApiResponse(result));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }
}

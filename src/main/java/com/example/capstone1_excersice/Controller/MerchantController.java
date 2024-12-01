package com.example.capstone1_excersice.Controller;


import com.example.capstone1_excersice.ApiResponse.ApiResponse;
import com.example.capstone1_excersice.Model.Merchant;
import com.example.capstone1_excersice.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getMerchant(){
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        merchantService.addMerchant(merchant);
        return ResponseEntity.status(201).body(new ApiResponse("Merchant is added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable Integer id, @RequestBody @Valid Merchant merchant , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if(merchantService.updateMerchant(id,merchant)) return ResponseEntity.ok(new ApiResponse("Merchant is updated"));
        else return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity updateMerchant(@PathVariable Integer id){

        if(merchantService.deleteMerchant(id)) return ResponseEntity.ok(new ApiResponse("Merchant is deleted"));
        else return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }
}

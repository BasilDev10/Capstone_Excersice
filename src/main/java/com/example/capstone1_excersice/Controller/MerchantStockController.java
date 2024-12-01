package com.example.capstone1_excersice.Controller;

import com.example.capstone1_excersice.ApiResponse.ApiResponse;
import com.example.capstone1_excersice.Model.MerchantStock;
import com.example.capstone1_excersice.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity getMerchantStock(){
        return ResponseEntity.ok(merchantStockService.getAllMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String result = merchantStockService.addMerchantStock(merchantStock);
        if(result.equalsIgnoreCase("added")) return ResponseEntity.status(201).body(new ApiResponse("Merchant stock is added"));
        else return ResponseEntity.status(400).body(new ApiResponse(result));

    }

    @PostMapping("/add-stock/{productId}/{merchantId}/{stock}")
    public ResponseEntity addStock(@PathVariable Integer productId , @PathVariable Integer merchantId, @PathVariable int stock){

        String result = merchantStockService.addMerchantStockToProduct(productId,merchantId,stock);
        if(result.equalsIgnoreCase("added")) return ResponseEntity.status(201).body(new ApiResponse("Merchant stock is added"));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }



    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable Integer id ,@RequestBody @Valid MerchantStock merchantStock ,Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        String result = merchantStockService.updateMerchantStock(id,merchantStock);
        if (result.equalsIgnoreCase("success")) return ResponseEntity.ok(new ApiResponse("Merchant Stock is updated"));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @PutMapping("/user-buy-product/{userId}/{productId}/{merchantId}")
    public ResponseEntity userByProduct(@PathVariable Integer userId , @PathVariable Integer productId ,@PathVariable Integer merchantId){
        String result = merchantStockService.userBuyProduct(userId,productId,merchantId);
        if(result.equalsIgnoreCase("success")) return ResponseEntity.status(200).body(new ApiResponse(result));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable Integer id ){

        if (merchantStockService.deleteMerchantStock(id)) return ResponseEntity.ok(new ApiResponse("Merchant Stock is deleted"));
        else return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock not found "));
    }

    // Extra endpoint 1
    // Users can return an order, which restocks the merchant's inventory and refunds the amount to balance the user.
    @PutMapping("/user-return-order/{userId}/{productId}/{merchantId}")
    public ResponseEntity userReturnOrder(@PathVariable Integer userId , @PathVariable Integer productId ,@PathVariable Integer merchantId){
        String result = merchantStockService.userReturnOrder(userId,productId,merchantId);
        if(result.equalsIgnoreCase("success")) return ResponseEntity.status(200).body(new ApiResponse(result));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }
    // Extra endpoint 2
    // When a user selects a product to purchase from the merchant's stock, this endpoint uses the product's ID to suggest similar products within the same category.
    @GetMapping("/get-suggestion-product-by-category/{productId}")
    public ResponseEntity suggestionProductByCategory(@PathVariable Integer productId){
        return ResponseEntity.ok(merchantStockService.suggestionProductByCategory(productId));
    }
    // Extra endpoint 3
    // When a user selects a product to purchase from the merchant's stock, this endpoint uses the merchant ID to suggest similar products within the same merchant
    @GetMapping("/get-suggestion-product-by-merchant/{merchantId}")
    public ResponseEntity suggestionProductByMerchant(@PathVariable Integer merchantId){
        return ResponseEntity.ok(merchantStockService.suggestionProductByMerchant(merchantId));
    }
    //Extra endpoint 4
    //When user purchase product and first check product is allowed coupon and applying coupon check is it correct and make discount for price
    @PutMapping("/user-buy-product/{userId}/{productId}/{merchantId}/{coupon}")
    public ResponseEntity userByProduct(@PathVariable Integer userId , @PathVariable Integer productId ,@PathVariable Integer merchantId , @PathVariable String coupon){
        String result = merchantStockService.userBuyProduct(userId,productId,merchantId , coupon);
        if(result.equalsIgnoreCase("success")) return ResponseEntity.status(200).body(new ApiResponse(result));
        else return ResponseEntity.status(400).body(new ApiResponse(result));
    }
}

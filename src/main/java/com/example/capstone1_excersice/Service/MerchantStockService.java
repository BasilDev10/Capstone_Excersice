package com.example.capstone1_excersice.Service;

import com.example.capstone1_excersice.Model.Merchant;
import com.example.capstone1_excersice.Model.MerchantStock;
import com.example.capstone1_excersice.Model.Product;
import com.example.capstone1_excersice.Model.User;
import com.example.capstone1_excersice.Repository.MerchantStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantStockRepository merchantStockRepository;
    private final ProductService productService;
    private final MerchantService merchantService;
    private final UserService userService;


    public List<MerchantStock> getAllMerchantStocks(){
        return merchantStockRepository.findAll();
    }


    public MerchantStock getMerchantStockById(Integer id){
        for (MerchantStock merchantStock : getAllMerchantStocks()){
            if (merchantStock.getId().equals(id))return merchantStock;
        }
        return null;
    }


    public MerchantStock getMerchantStockById(Integer productId,Integer merchantId){
        for (MerchantStock merchantStock : getAllMerchantStocks()){
            if (merchantStock.getProductId().equals(productId) && merchantStock.getMerchantId().equals(merchantId))return merchantStock;
        }
        return null;
    }

    public List<MerchantStock> suggestionProductByCategory(Integer productId){
        List<MerchantStock> suggestedProduct = new ArrayList<>();

        Product product = productService.getProductById(productId);
        if(product == null) return suggestedProduct;

        for (MerchantStock merchantStock : getAllMerchantStocks()){
            if (productService.getProductById(merchantStock.getProductId()).getCategoryId().equals(product.getCategoryId())){
                suggestedProduct.add(merchantStock);
            }
        }
        return suggestedProduct;
    }


    public List<MerchantStock> suggestionProductByMerchant(Integer merchantId){
        List<MerchantStock> suggestedProduct = new ArrayList<>();

        Merchant merchant = merchantService.getMerchantById(merchantId);
        if(merchant == null) return suggestedProduct;

        for (MerchantStock merchantStock : getAllMerchantStocks()){
            if (merchantService.getMerchantById(merchantStock.getMerchantId()).getId().equals(merchantId)){
                suggestedProduct.add(merchantStock);
            }
        }
        return suggestedProduct;
    }

    public String addMerchantStock(MerchantStock merchantStock){

        if (productService.getProductById(merchantStock.getProductId()) == null) return "Error: productId ("+merchantStock.getProductId()+") not found ";
        else if(merchantService.getMerchantById(merchantStock.getMerchantId()) == null) return "Error: merchantId ("+merchantStock.getMerchantId()+") not found ";
        else if(getMerchantStockById(merchantStock.getProductId() , merchantStock.getMerchantId()) != null) return "Error : duplicated merchant stock";
        else {

            merchantStockRepository.save(merchantStock);
            return "added";
        }


    }

    public String addMerchantStockToProduct(Integer productId,Integer merchantId , int stock){

        if (productService.getProductById(productId) == null) return "Error: productId ("+productId+") not found ";
        if(merchantService.getMerchantById(merchantId) == null) return "Error: merchantId ("+merchantId+") not found ";

        if(getMerchantStockById(productId , merchantId) != null) return "Error : duplicated merchant stock";
        else {
            MerchantStock merchantStock = new MerchantStock();
            merchantStock.setProductId(productId);
            merchantStock.setMerchantId(merchantId);
            merchantStock.setStock(stock);
            merchantStockRepository.save(merchantStock);
            return "merchant stock added";
        }


    }



    public String updateMerchantStock(Integer id, MerchantStock merchantStock){

        if (productService.getProductById(merchantStock.getProductId()) == null) return "Error: productId ("+merchantStock.getProductId()+") not found ";
        else if(merchantService.getMerchantById(merchantStock.getMerchantId()) == null) return "Error: merchantId ("+merchantStock.getMerchantId()+") not found ";

        if (merchantStockRepository.existsById(id)){
            merchantStock.setId(id);
            merchantStockRepository.save(merchantStock);
            return "success";
        }
        return "Error: merchant stock not found";
    }

    public String userBuyProduct(Integer userId,Integer productId , Integer merchantId){

        MerchantStock merchantStock = getMerchantStockById(productId,merchantId);
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);
        Merchant merchant = merchantService.getMerchantById(merchantId);

        if(merchantStock == null )return "Error: Merchant stock with productId ("+productId+") and merchantId ("+merchantId+")  not found";
        else if ( user == null) return "Error: userId ("+userId+") not found";
        else if(product == null) return "Error: productId ("+productId+") not found";
        else if(merchant == null ) return  "Error: merchantId ("+merchantId+") not found";
        else if(merchantStock.getStock() <= 0) return "Error: product is out of stock";
        else if (user.getBalance() < product.getPrice()) return "Error: user balance is less then product price";
        else{
            user.setBalance(user.getBalance() - product.getPrice());
            userService.updateUser(user.getId(),user);
            merchantStock.setStock(merchantStock.getStock()-1);
            updateMerchantStock(merchantStock.getId(),merchantStock);
            return "success";
        }
    }


    public String userBuyProduct(Integer userId,Integer productId , Integer merchantId , String coupon){
        MerchantStock merchantStock = getMerchantStockById(productId,merchantId);
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);
        Merchant merchant = merchantService.getMerchantById(merchantId);

        if(merchantStock == null )return "Error: Merchant stock with productId ("+productId+") and merchantId ("+merchantId+")  not found";
        else if ( user == null) return "Error: userId ("+userId+") not found";
        else if(product == null) return "Error: productId ("+productId+") not found";
        else if(merchant == null ) return  "Error: merchantId ("+merchantId+") not found";
        else if(merchantStock.getStock() <= 0) return "Error: product is out of stock";
        else if( product.getApplyCoupon().equals(false)) return "Error: this product does not apply coupon";
        else if (!product.getCoupon().equalsIgnoreCase(coupon)) return "Error: coupon is wrong";
        else if(product.getCouponDiscountPercentage() == 0) return "Error: coupon discount percentage is 0";

        double price = product.getPrice();
        double couponDiscountPercentage = (double) product.getCouponDiscountPercentage() / 100;
        double amount = price -(price * couponDiscountPercentage);

        if (user.getBalance() < amount) return "Error: user balance is less then product price";
        else{
            user.setBalance(user.getBalance() - amount);
            merchantStock.setStock(merchantStock.getStock()-1);
            userService.updateUser(user.getId(),user);
            updateMerchantStock(merchantStock.getId(),merchantStock);
            return "success";
        }
    }

    public String userReturnOrder(Integer userId,Integer productId , Integer merchantId){

        MerchantStock merchantStock = getMerchantStockById(productId,merchantId);
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);
        Merchant merchant = merchantService.getMerchantById(merchantId);

        if(merchantStock == null )return "Error: Merchant stock with productId ("+productId+") and merchantId ("+merchantId+")  not found";
        else if ( user == null) return "Error: userId ("+userId+") not found";
        else if(product == null) return "Error: productId ("+productId+") not found";
        else if(merchant == null ) return  "Error: merchantId ("+merchantId+") not found";
        else{
            user.setBalance(user.getBalance() + product.getPrice());
            merchantStock.setStock(merchantStock.getStock()+1);
            userService.updateUser(user.getId(),user);
            updateMerchantStock(merchantStock.getId(),merchantStock);
            return "success";
        }
    }
    public Boolean deleteMerchantStock(Integer id){

        if(merchantStockRepository.existsById(id)){
            merchantStockRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

package com.example.capstone1_excersice.Service;

import com.example.capstone1_excersice.Model.Merchant;
import com.example.capstone1_excersice.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public List<Merchant> getAllMerchants(){
        return merchantRepository.findAll();
    }



    public Merchant getMerchantById(Integer id ){
        for(Merchant merchant : getAllMerchants()){
            if (merchant.getId().equals(id)){
                return merchant;
            }
        }
        return null;
    }
    public void addMerchant(Merchant merchant){

        merchantRepository.save(merchant);
    }

    public Boolean updateMerchant(Integer id, Merchant merchant){


        if (merchantRepository.existsById(id)){
            merchant.setId(id);
            merchantRepository.save(merchant);
            return true;
        }

        return false;
    }


    public Boolean deleteMerchant(Integer id){

        if (merchantRepository.existsById(id)){
            merchantRepository.deleteById(id);
            return true;
        }

        return false;
    }
}

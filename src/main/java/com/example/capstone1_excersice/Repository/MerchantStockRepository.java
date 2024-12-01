package com.example.capstone1_excersice.Repository;

import com.example.capstone1_excersice.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantStockRepository extends JpaRepository<MerchantStock,Integer> {
}

package com.example.capstone1_excersice.Repository;

import com.example.capstone1_excersice.Model.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRequestRepository extends JpaRepository<TransferRequest,Integer> {
}

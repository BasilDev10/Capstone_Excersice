package com.example.capstone1_excersice.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MerchantStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Error: productId is empty")
    @Column(columnDefinition = "int not null")
    private Integer productId;
    @NotEmpty(message = "Error: merchantId is empty")
    @Column(columnDefinition = "int not null")
    private Integer merchantId;
    @NotNull(message = "Error : stock is null")
    @Positive(message = "Error: stock must be positive")
    @Min(value = 10 , message = "Error: stock must be at least 10 at start")
    @Column(columnDefinition = "int not null")
    private Integer stock;
}

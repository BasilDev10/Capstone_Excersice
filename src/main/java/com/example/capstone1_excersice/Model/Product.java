package com.example.capstone1_excersice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Error: name is empty")
    @Size(min = 3 , message = "Error: name length must is more then 3")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;
    @NotNull(message = "Error: price is null")
    @Positive(message = "Error: price is must be positive")
    @Column(columnDefinition = "float not null")
    private Double price;
    @NotEmpty(message = "Error: categoryId is empty")
    @Column(columnDefinition = "int not null")
    private Integer categoryId;
    @NotEmpty(message = "Error: coupon is empty")
    @Size(min = 4 , message = "Error: coupon length is more then 4")
    @Column(columnDefinition = "varchar(10) not null")
    private String coupon;
    @NotNull(message = "Error: coupon Discount Percentage is null")
    @PositiveOrZero(message = "Error: coupon Discount Percentage is must be positive or zero")
    @Max(value = 100, message = "Error: coupon Discount Percentage the max is 100")
    @Column(columnDefinition = "int not null")
    private Integer couponDiscountPercentage;
    @NotNull(message = "Error: applyCoupon is null")
    @Column(columnDefinition = "bool not null")
    private Boolean applyCoupon;
}

package com.example.capstone1_excersice.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Check(constraints = "status in ('approved', 'rejected', 'pending')")
public class TransferRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: reason is empty!")
    @Column(columnDefinition = "varchar(25) not null")
    private String reason;
    @NotNull(message = "Error: amount is null!")
    @Positive(message = "Error: amount must be positive!")
    @Column(columnDefinition = "double not null")
    private Double amount;
    @NotNull(message = "Error: userIdFrom is null!")
    @Column(columnDefinition = "int not null")
    private Integer userIdFrom;
    @NotNull(message = "Error: userIdTo is null!")
    @Column(columnDefinition = "int not null")
    private Integer userIdTo;
    @NotEmpty(message = "Error: status is empty!")
    @Column(columnDefinition = "varchar(25) not null")
    @Pattern(regexp = "approved|rejected|pending" , message ="Error: only accept approved|rejected|pending")
    private String status;
}

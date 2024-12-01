package com.example.capstone1_excersice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "balance >= 0")
@Check(constraints = "role = 'Admin' or role = 'Customer'")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Error: username is empty!")
    @Size(min = 6 , message = "Error: username length must is more then 6")
    @Column(columnDefinition = "varchar(25) not null unique")
    private String username;
    @NotEmpty(message = "Error: password is empty!")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$" ,message = "Error:1-The password must be more than 6 characters long.\n" +
            "2-It must include both alphabetic characters (letters) and digits.")
    private String password;
    @NotEmpty(message = "Error: email is empty!")
    @Email(message = "Error: wrong email format")
    private String email;
    @NotEmpty(message = "Error: email is empty!")
    @Pattern(regexp = "Admin|Customer" , message = "Error: role only accept Admin or Customer")
    private String role;
    @NotNull(message = "Error: balance is null")
    @PositiveOrZero(message = "Error: balance must be positive or zero")
    private double balance;
}

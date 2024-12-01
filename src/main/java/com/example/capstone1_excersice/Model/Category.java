package com.example.capstone1_excersice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @NotEmpty(message = "Error: name is empty")
    @Size(min = 3 , message = "Error: name length must is more then 3")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;

}

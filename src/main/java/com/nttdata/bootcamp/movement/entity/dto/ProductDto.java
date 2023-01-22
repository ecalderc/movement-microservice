package com.nttdata.bootcamp.movement.entity.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotNull
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank
    private String typeCustomer;

    @Pattern(regexp = "\\d{10}")
    private String accountNumber;

    @NotBlank
    private String accountType;

    @Min(0)
    private double balance;

}

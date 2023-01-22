package com.nttdata.bootcamp.movement.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCustomerDto {

    @NotNull
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank
    private String name;

    @NotBlank
    private String surName;

    @NotBlank
    private String address;

}

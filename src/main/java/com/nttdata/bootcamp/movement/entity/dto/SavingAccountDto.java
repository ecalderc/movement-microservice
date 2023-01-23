package com.nttdata.bootcamp.movement.entity.dto;

import com.nttdata.bootcamp.movement.entity.enums.TypeCurrency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SavingAccountDto {
    private String id;
    private BigDecimal balance;
    private TypeCurrency currency;
    private String accountNumber;
    private Integer cvc;
    private String dni;
}

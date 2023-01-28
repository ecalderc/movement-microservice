package com.nttdata.bootcamp.movement.entity.dto;

import com.nttdata.bootcamp.movement.entity.enums.TypeCurrency;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CurrentAccountDto {
    private String id;
    private BigDecimal balance;
    private TypeCurrency currency;
    private String accountNumber;
    private Integer cvc;
    private String dni;
}

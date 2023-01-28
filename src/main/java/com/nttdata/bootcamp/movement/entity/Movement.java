package com.nttdata.bootcamp.movement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nttdata.bootcamp.movement.entity.enums.TypeCurrency;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "movement")
public class Movement {

    @Id
    private String id;

    @Size(min = 16, max = 16)
    private String accountNumber;

    private BigDecimal amount;

    @NotEmpty
    @Size(min = 8, max = 8)
    private String dni;

    //@Enumerated(EnumType.STRING)
    private TypeCurrency currency;

    @Range(min = 100, max = 999)
    private Integer cvc;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private Date creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private Date modificationDate;

}

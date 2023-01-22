package com.nttdata.bootcamp.movement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;

    @NotNull
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank
    private String typeCustomer;

    @NotNull
    private Boolean flagVip;

    @NotNull
    private Boolean flagPyme;

    @NotBlank
    private String name;

    @NotBlank
    private String surName;

    @NotBlank
    private String address;

    @NotBlank
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private Date creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private Date modificationDate;

}

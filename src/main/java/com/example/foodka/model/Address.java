package com.example.foodka.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(generator = "addressIdSeq")
    @SequenceGenerator(name = "addressIdSeq", sequenceName = "address_id_seq", allocationSize = 1)
    private Integer id;
    private String title;
    private String subTitle;
    private Double lat;
    private Double lon;
    @ManyToOne
    private Users user;
}

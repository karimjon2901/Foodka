package com.example.foodka.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Translator name;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Translator description;
    private Double price;
    private Integer count;
    @ManyToOne
    private Category category;
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime date;
}
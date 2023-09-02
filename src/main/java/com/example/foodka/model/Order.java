package com.example.foodka.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private Integer id;
    @ManyToOne
    private Users user;
    private Double price;
    private String description;
    private Integer status = 0;
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany
    private List<Product> products;
    @ManyToOne
    private Address address;
    private Integer time;
}

package com.example.foodka.model;

import jakarta.persistence.*;
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
public class Orders {
    @Id
    @GeneratedValue(generator = "ordersIdSeq")
    @SequenceGenerator(name = "ordersIdSeq", sequenceName = "orders_id_seq", allocationSize = 1)
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

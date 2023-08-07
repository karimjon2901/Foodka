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
public class Users {
    @Id
    @GeneratedValue(generator = "usersIdSeq")
    @SequenceGenerator(name = "usersIdSeq", sequenceName = "users_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String phoneNumber;
    private String password;
    private String role="USER";
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdAt;
}

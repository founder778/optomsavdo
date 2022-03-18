package com.company.optomsavdo.telegramBot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer o_id;
    private Integer o_number;
    private Double o_price;
    private String date;
    private String status;
    private String name;

    @ManyToOne
    @JoinColumn(name = "a_o_id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "o_p_id")
    private ProductEntity product;

}

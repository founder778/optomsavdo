package com.company.optomsavdo.AdminPage.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntityAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer p_id;
    private String p_name;
    private String p_caption;
    private String p_price;
    private String p_type;
    @JoinColumn(name = "img")
    private String img;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "menu_id")
    private MenuEntityAdmin menu;
}

package com.company.optomsavdo.AdminPage.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "menu")
public class MenuEntityAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer m_id;
    private String m_name;

}

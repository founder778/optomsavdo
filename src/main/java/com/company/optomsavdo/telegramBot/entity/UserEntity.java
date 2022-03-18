package com.company.optomsavdo.telegramBot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "agents")
public class UserEntity {
    @Id
    private Integer a_id;
    private String a_name;
    private String a_surname;
    private String a_phone;
    private String a_login;
    private String a_password;
    private String status;

}

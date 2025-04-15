package com.team.youarelikemetoo.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;

    private String oauthId;

    private String name;

    private String pw;

    private String role;

    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String job;
    private String addr;


}

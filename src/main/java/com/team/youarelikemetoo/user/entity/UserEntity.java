package com.team.youarelikemetoo.user.entity;

import com.team.youarelikemetoo.user.dto.UserDTO;
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

    public void changeUserInfo(UserDTO userDTO){
        this.name = userDTO.getName();
        this.age = userDTO.getAge();
        this.gender = userDTO.getGender();
        this.job = userDTO.getJob();
        this.addr = userDTO.getAddr();

        return;
    }


}

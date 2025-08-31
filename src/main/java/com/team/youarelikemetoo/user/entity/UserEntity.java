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
    @Column(name = "user_id")
    private Long id;

    @Column(name = "oauth_provider")
    private String oauthProvider;

    @Column(name = "oauth_id")
    private String oauthId;

    private String name;

    private String pw;

    private String role;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String job;
    private String addr;

    @Column(name = "instagram_id")
    private String instagramId;

    @Column(name = "introduction")
    private String introduction;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfileImage userProfileImage;

    public void changeUserInfo(UserDTO userDTO){
        this.name = userDTO.getName();
        this.age = userDTO.getAge();
        this.gender = userDTO.getGender();
        this.job = userDTO.getJob();
        this.addr = userDTO.getAddr();
        this.instagramId = userDTO.getInstagramId();
        this.introduction = userDTO.getIntroduction();

        return;
    }


}

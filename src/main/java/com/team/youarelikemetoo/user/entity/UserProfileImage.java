package com.team.youarelikemetoo.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user_profile_image")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_image_id")
    private Long id;

    @Column(name = "unique_filename")
    private String uniqueFilename;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "blob_name")
    private String blobName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    @Builder
    public UserProfileImage(String uniqueFilename, String imageUrl, String blobName, UserEntity user) {
        this.uniqueFilename = uniqueFilename;
        this.imageUrl = imageUrl;
        this.blobName = blobName;
        this.user = user;
    }
}

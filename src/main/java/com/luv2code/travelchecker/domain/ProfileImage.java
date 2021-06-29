package com.luv2code.travelchecker.domain;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import com.luv2code.travelchecker.domain.enums.ExtensionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile_image")
public class ProfileImage extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "image_file_path")
    private String imageFilePath;

    @Column(name = "extension_type")
    @Enumerated(EnumType.STRING)
    private ExtensionType extensionType;

    @OneToOne(mappedBy = "profileImage")
    private User user;

}

package com.luv2code.travelchecker.domain;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import com.luv2code.travelchecker.domain.enums.ExtensionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "IMAGE")
public class ProfileImage extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "image_file_path")
    private String imageFilePath;

    @Column(name = "extension")
    @Enumerated(EnumType.STRING)
    private ExtensionType extensionType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @OneToOne(mappedBy = "profileImage")
    private User user;

}

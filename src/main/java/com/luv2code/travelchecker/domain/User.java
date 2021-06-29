package com.luv2code.travelchecker.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.luv2code.travelchecker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity implements Serializable {

    @Column(name = "first_name")
    @NotBlank(message = "{validation.user.firstName.notBlank}")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "profile_image_id",
            referencedColumnName = "id")
    private ProfileImage profileImage;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "user_marker",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "marker_id"))
    private List<Marker> markers;

    public void addRole(final Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }

        roles.add(role);
    }
}

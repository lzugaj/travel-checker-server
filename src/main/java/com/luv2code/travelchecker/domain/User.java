package com.luv2code.travelchecker.domain;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import com.luv2code.travelchecker.validation.Email;
import com.luv2code.travelchecker.validation.Password;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "username" })
        }
)
public class User extends BaseEntity implements Serializable {

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "{user.firstName.blank}")
    @Size(min = 2, message = "{user.firstName.size}")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "{user.lastName.blank}")
    @Size(min = 2, message = "{user.lastName.size}")
    private String lastName;

    @Column(name = "email", nullable = false)
    @Email(message = "{user.email.invalid}")
    private String email;

    @Column(name = "username", nullable = false)
    @NotBlank(message = "{user.username.blank}")
    @Size(min = 5, message = "{user.username.size}")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "{user.password.blank}")
    @Password(message = "{user.password.invalid}")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    public void addMarker(final Marker marker) {
        if (markers == null) {
            markers = new ArrayList<>();
        }

        markers.add(marker);
        marker.setUsers(Collections.singletonList(this));
    }
}

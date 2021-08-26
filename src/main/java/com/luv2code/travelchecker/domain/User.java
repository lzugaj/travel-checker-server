package com.luv2code.travelchecker.domain;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import com.luv2code.travelchecker.validation.Email;
import com.luv2code.travelchecker.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class User extends BaseEntity {

    @Column(name = "first_name")
    @NotBlank(message = "{user.firstName.blank}")
    @Size(min = 2, message = "{user.firstName.size}")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "{user.lastName.blank}")
    @Size(min = 2, message = "{user.lastName.size}")
    private String lastName;

    @Column(name = "email")
    @Email(message = "{user.email.invalid}")
    private String email;

    @Column(name = "username")
    @NotBlank(message = "{user.username.blank}")
    @Size(min = 5, message = "{user.username.size}")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "{user.password.blank}")
    @Password(message = "{user.password.invalid}")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ManyToMany(
            mappedBy = "users",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Marker> markers;

    public void addRole(final Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }

        roles.add(role);
    }
}

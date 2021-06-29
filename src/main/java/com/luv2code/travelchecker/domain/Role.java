package com.luv2code.travelchecker.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luv2code.travelchecker.domain.base.BaseEntity;
import com.luv2code.travelchecker.domain.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends BaseEntity implements Serializable {

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}

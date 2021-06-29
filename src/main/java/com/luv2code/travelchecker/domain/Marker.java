package com.luv2code.travelchecker.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.luv2code.travelchecker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "marker")
@EqualsAndHashCode(callSuper = true)
public class Marker extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "event_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate eventDate;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "should_visit_again")
    private Boolean shouldVisitAgain;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "marker_id", nullable = false)
    private Coordinate coordinate;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "markers")
    private List<User> users;

    public void addUser(final User user) {
        if (users == null) {
            users = new ArrayList<>();
        }

        users.add(user);
    }
}

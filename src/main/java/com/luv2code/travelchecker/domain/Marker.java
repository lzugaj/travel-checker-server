package com.luv2code.travelchecker.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.luv2code.travelchecker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "marker")
public class Marker extends BaseEntity implements Serializable {

    @Column(name = "name", nullable = false)
    @NotBlank(message = "{marker.name.blank}")
    @Size(min = 2, message = "{marker.name.size}")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "event_date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate eventDate;

    @Column(name = "grade", nullable = false)
    @NotNull(message = "{marker.grade.null}")
    private Integer grade;

    @Column(name = "should_visit_again", nullable = false)
    @NotNull(message = "{marker.shouldVisitAgain.null}")
    private Boolean shouldVisitAgain;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "coordinate_id", nullable = false)
    private Coordinate coordinate;

    @ManyToMany(mappedBy = "markers", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> users;

    public void addUser(final User user) {
        if (users == null) {
            users = new ArrayList<>();
        }

        users.add(user);
    }

}

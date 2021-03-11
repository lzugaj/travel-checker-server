package com.luv2code.travelchecker.domain;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MARKER")
@EqualsAndHashCode(callSuper = true)
public class Marker extends BaseEntity implements Serializable {

    private String name;

    private String description;

    private Date eventDate;

    private Integer grade;

    private Boolean shouldVisitAgain;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "coordinate_id",
            referencedColumnName = "id"
    )
    private Coordinate coordinate;
}

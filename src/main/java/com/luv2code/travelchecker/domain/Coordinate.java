package com.luv2code.travelchecker.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luv2code.travelchecker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COORDINATE")
@EqualsAndHashCode(callSuper = true)
public class Coordinate extends BaseEntity implements Serializable {

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @JsonBackReference
    @OneToOne(mappedBy = "coordinate")
    private Marker marker;
}

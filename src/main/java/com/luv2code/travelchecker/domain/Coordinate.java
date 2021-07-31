package com.luv2code.travelchecker.domain;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordinate")
public class Coordinate extends BaseEntity implements Serializable {

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coordinate", orphanRemoval = true)
    private List<Marker> markers;

}

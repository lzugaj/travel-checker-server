package com.luv2code.travelchecker.domain;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordinate")
@EqualsAndHashCode(callSuper = true)
public class Coordinate extends BaseEntity implements Serializable {

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(mappedBy = "coordinate")
    private List<Marker> markers;

    public void addMarker(final Marker marker) {
        if (markers == null) {
            markers = new ArrayList<>();
        }

        markers.add(marker);
    }
}

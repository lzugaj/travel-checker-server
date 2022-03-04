package com.luv2code.travelchecker.domain;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token", unique = true)
    private UUID token;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

}

package com.luv2code.travelchecker.domain;

import com.luv2code.travelchecker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "password_reset_token")
public class ResetPasswordToken extends BaseEntity {

    @Column(name = "token", unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ResetPasswordToken)) {
            return false;
        }

        final ResetPasswordToken resetPasswordToken = (ResetPasswordToken) other;
        return resetPasswordToken.token.equals(((ResetPasswordToken) other).token)
                && resetPasswordToken.getUser().equals(((ResetPasswordToken) other).user);
    }
}

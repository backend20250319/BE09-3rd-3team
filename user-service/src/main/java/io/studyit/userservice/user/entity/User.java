package io.studyit.userservice.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}

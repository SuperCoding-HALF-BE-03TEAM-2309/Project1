package com.supercoding.Project1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private String nickname;

    private Timestamp created_at;
}

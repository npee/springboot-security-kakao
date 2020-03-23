package com.npeeproject.api.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String email;
}

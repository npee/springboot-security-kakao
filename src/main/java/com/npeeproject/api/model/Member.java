package com.npeeproject.api.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "양식에 맞지 않습니다")
    private String email;

    @NotNull
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "[0-9]{11}", message = "11자리의 숫자로 구성되어야 합니다")
    //@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?([0-9]{3}|[0-9]{4})[.-]?([0-9]{4})$", message = "11자리의 숫자로 구성되어야 합니다")
    private String phoneNumber;


}

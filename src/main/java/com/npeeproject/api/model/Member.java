package com.npeeproject.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Table(name = "member")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "이메일을 입력해주세요")
    // @Email(message = "양식에 맞지 않습니다")
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // @NotNull
    @Column(nullable = true, length = 50)
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @NotNull
    @Column(length = 100)
    private String name;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "[0-9]{11}", message = "11자리의 숫자로 구성되어야 합니다")
    @NotNull
    //@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?([0-9]{3}|[0-9]{4})[.-]?([0-9]{4})$", message = "11자리의 숫자로 구성되어야 합니다")
    private String phoneNumber;

    @Column(length = 100)
    private String provider;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.sparta.shoppingmall.user.entity;

import com.sparta.shoppingmall.base.entity.Timestamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter

public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "username: 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
    private String username;

    @Column(nullable = false)
    private String password;


    private String recentPassword;

    private String recentPassword2;

    private String recentPassword3;

    @Column(nullable = false)
    @Email(message = "이메일 형식이 올바르지 않습니다.")  // username@damain.com
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "varchar(30) default 'USER'")
    @Enumerated(value = EnumType.STRING)
    private UserType userType;   // UserRole

    @Column(columnDefinition = "varchar(30)")
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;  // UserStatus

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime statusChangedAt;

    private String refreshToken;

    @Builder
    public User(String username, String password, String name, String email, String address, UserStatus userStatus,UserType userType ,LocalDateTime statusChangedAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.userStatus = userStatus;
        this.userType = userType;
        this.statusChangedAt = statusChangedAt;
    }


//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private final List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private final List<Product> products = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private final List<Cart> carts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private final List<Like> Likes = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private final List<Order> orders = new ArrayList<>();

   // 프로필 수정
    public void editProfile(String username, String email, String address) {
        this.username = username;
        this.address = address;
        this.email = email;
    }

    //비밀번호 변경
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    @Transactional
    public void refreshTokenReset(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}

package com.umc_spring.Heart_Hub.user.model;

import com.umc_spring.Heart_Hub.Report.model.enums.ReportStatus;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.mission.UserMissionStatus;
import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 45)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 1)
    private String gender;

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String userImgUrl;

    @Column(nullable = false, length = 45)
    private String nickname;

    @Column(length = 45)
    private String userMessage;

    @Column(nullable = false, length = 1)
    private String marketingStatus;

    private LocalDate birth;

    private LocalDate datingDate;

    @Column(nullable = false, length = 1)
    private String status;

    @OneToOne
    @JoinColumn(name = "mate")
    private User user;

    @Enumerated(EnumType.STRING)
    private ReportStatus reportedStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserMissionStatus> userMissionStatusList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.getRole()));

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void mateMatching(User mate){
        this.user = mate;
    }

    public void warnUser() {
        this.reportedStatus = ReportStatus.WARNED;
    }

    public void deleteUserContents() {
        this.reportedStatus = ReportStatus.CONTENT_DELETED;
    }

    public void suspendUser() {
        this.reportedStatus = ReportStatus.ACCOUNT_SUSPENDED;
    }

    public void modifyUserAuthority() { this.role = Role.ROLE_ADMIN; }
    public void modifyUserNickName(String nickname){
        this.nickname = nickname;
    }
    public void modifyUserMessage(String userMessage){
        this.userMessage = userMessage;
    }
    public void modifyUserImgUrl(String imgUrl){
        this.userImgUrl = imgUrl;
    }
}

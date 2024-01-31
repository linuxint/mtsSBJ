package com.devkbil.mtssbj.member;

import com.devkbil.mtssbj.admin.organ.DeptVO;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Schema(description = "사용자 : UserVO")
@XmlRootElement(name = "UserVO")
@XmlType(propOrder = {"userno", "userid", "userpw", "usernm", "photo", "userrole", "userpos", "ip", "deptno", "deptnm",
        "photofile"})
@Getter
@Setter
@Entity(name = "com_user")
public class UserVO implements UserDetails {

    @Id
    @Schema(description = "사용자 번호")
    private String userno; // 사용자 번호

    @Schema(description = "ID")
    @Column(unique = true)
    private String userid; // ID

    @Schema(description = "비밀번호")
    private String userpw; // 비밀번호

    @Schema(description = "이름")
    private String usernm; // 이름

    @Schema(description = "권한")
    private String userrole; // 권한

    @Schema(description = "메일서비스")
    private String userpos; // 메일서비스

    @Schema(description = "remember")
    @Transient
    private String remember;

    @Schema(description = "사진")
    private String photo; // 사진

    @Schema(description = "아이피")
    @Transient
    private String ip; // 아이피

    @OneToOne
    @JoinColumn(name = "deptno")
    @Schema(description = "deptVO join")
    private DeptVO deptVO;

    @Schema(description = "deptno")
    @Transient
    private String deptno;

    @Schema(description = "deptnm")
    @Transient
    private String deptnm;

    @Schema(description = "사진")
    @Transient
    private MultipartFile photofile; // 사진

    @Schema(description = "삭제여부")
    private String deleteflag; // 삭제여부

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userpw;
    }

    @Override
    public String getUsername() {
        return this.userid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserVO) {
            return this.userid.equals(((UserVO) obj).userid);
        }
        return false;
    }
}

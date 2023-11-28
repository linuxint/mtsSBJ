package com.devkbil.mtssbj.member;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "로그인 : LoginVO")
@XmlRootElement(name = "LoginVO")
@XmlType(propOrder = {"userid", "userpw", "remember"})
@Getter
@Setter
@Entity(name = "com_user")
public class LoginVO {

    @Id
    @Schema(description = "사용자 번호")
    private String userno;

    @Schema(description = "ID")
    @Column(unique = true)
    private String userid;

    @Schema(description = "비밀번호")
    private String userpw;

    @Schema(description = "이름")
    private String usernm;

    @Schema(description = "remember")
    @Transient
    private String remember;

    @Schema(description = "권한")
    private String userrole;
}

package com.devkbil.mtssbj.member;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "사용자 : UserVO")
@XmlRootElement(name = "UserVO")
@XmlType(propOrder = {"userno", "userid", "userpw", "usernm", "photo", "userrole", "userpos", "ip", "deptno", "deptnm",
        "photofile"})
@Getter
@Setter
public class UserVO {

    @Schema(description = "사용자 번호")
    private String userno; // 사용자 번호

    @Schema(description = "ID")
    private String userid; // ID

    @Schema(description = "비밀번호")
    private String userpw; // 비밀번호

    @Schema(description = "이름")
    private String usernm; // 이름

    @Schema(description = "사진")
    private String photo; // 사진

    @Schema(description = "권한")
    private String userrole; // 권한

    @Schema(description = "메일서비스")
    private String userpos; // 메일서비스

    @Schema(description = "아이피")
    private String ip; // 아이피

    @Schema(description = "부서코드")
    private String deptno; // 부서코드

    @Schema(description = "부서명")
    private String deptnm; // 부서명

    @Schema(description = "사진")
    private MultipartFile photofile; // 사진

}

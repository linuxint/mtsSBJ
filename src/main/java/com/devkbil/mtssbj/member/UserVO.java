package com.devkbil.mtssbj.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "사용자 : UserVO", description = "사용자정보")
@XmlRootElement(name = "UserVO")
@XmlType(propOrder = {"userno", "userid", "userpw", "usernm", "photo", "userrole", "userpos", "ip", "deptno", "deptnm", "photofile"})
@Getter
@Setter
public class UserVO {
    
    @ApiModelProperty(value = "사용자 번호")
    private String userno; // 사용자 번호
    @ApiModelProperty(value = "ID")
    private String userid; // ID
    @ApiModelProperty(value = "비밀번호")
    private String userpw; // 비밀번호
    @ApiModelProperty(value = "이름")
    private String usernm; // 이름
    @ApiModelProperty(value = "사진")
    private String photo; // 사진
    @ApiModelProperty(value = "권한")
    private String userrole; // 권한
    @ApiModelProperty(value = "메일서비스")
    private String userpos; // 메일서비스
    @ApiModelProperty(value = "아이피")
    private String ip; // 아이피
    @ApiModelProperty(value = "부서코드")
    private String deptno; // 부서코드
    @ApiModelProperty(value = "부서명")
    private String deptnm; // 부서명
    @ApiModelProperty(value = "사진")
    private MultipartFile photofile; // 사진
    
}

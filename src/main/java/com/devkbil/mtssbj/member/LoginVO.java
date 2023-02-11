package com.devkbil.mtssbj.member;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.devkbil.mtssbj.common.util.SecurityUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@ApiModel(value = "로그인 : LoginVO", description = "LoginVO")
@XmlRootElement(name = "LoginVO")
@XmlType(propOrder = {"userid", "userpw", "remember"})
@Getter
@Setter
public class LoginVO {

    @ApiModelProperty(value = "메일서비스")
    private String userid;
    @ApiModelProperty(value = "메일서비스")
    private String userpw;
    @ApiModelProperty(value = "메일서비스")
    private String remember;

    @SneakyThrows
    public void setUserpw(String userpw) {
        this.userpw = SecurityUtil.sha256(userpw);
    }
}

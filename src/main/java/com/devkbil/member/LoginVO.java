package com.devkbil.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
    
}

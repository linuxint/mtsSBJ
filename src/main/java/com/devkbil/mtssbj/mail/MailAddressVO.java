package com.devkbil.mtssbj.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "메일주소 : MailAddressVO")
@XmlRootElement(name = "MailAddressVO")
@XmlType(propOrder = {"emno", "eatype", "eaaddress", "easeq"})
@Getter
@Setter
public class MailAddressVO {

    @Schema(description = "메일번호")
    private String emno; // 메일번호
    @Schema(description = "순번")
    private String eatype; // 순번
    @Schema(description = "주소종류")
    private String eaaddress; // 주소종류
    @Schema(description = "메일주소")
    private Integer easeq; // 메일주소

} 

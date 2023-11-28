package com.devkbil.mtssbj.mail;

import java.util.ArrayList;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "메일 : ImportMailVO")
@XmlRootElement(name = "ImportMailVO")
@XmlType(propOrder = {"from", "to", "cc", "bcc", "file", "subject", "conents", "regdate"})
@Getter
@Setter
public class ImportMailVO {

    @Schema(description = "발신인")
    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String from; // 발신인

    @Schema(description = "수신인")
    private ArrayList<String> to = new ArrayList<String>(); // 수신인
    @Schema(description = "참조자")
    private ArrayList<String> cc = new ArrayList<String>(); // 참조자
    @Schema(description = "숨은참조자")
    private ArrayList<String> bcc = new ArrayList<String>(); // 숨은참조자
    @Schema(description = "첨부파일")
    private ArrayList<String> file = new ArrayList<String>(); // 첨부파일

    @Schema(description = "제목")
    private String subject; // 제목
    @Schema(description = "내용")
    private String conents; // 내용
    @Schema(description = "작성일")
    private String regdate; // 작성일

}

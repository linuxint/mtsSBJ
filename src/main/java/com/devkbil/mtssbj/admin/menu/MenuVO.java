package com.devkbil.mtssbj.admin.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "메뉴 : MenuVO")
@XmlRootElement(name = "MenuVO")
@XmlType(propOrder = {"mnu_no", "mnu_nm", "mnu_parent", "mnu_type"})
@Getter
@Setter
public class MenuVO {

        @Schema(description = "메뉴ID")
        private String mnuNo;

        @Schema(description = "상위메뉴ID")
        private String mnuParent;

        @Schema(description = "메뉴업무구분코드")
        private String mnuType;

        @Schema(description = "메뉴명")
        private String mnuNm;
        @Schema(description = "설명")
        private String mnuDesc;
        @Schema(description = "메뉴링크")
        private String mnuTarget;
        @Schema(description = "파일명")
        private String mnuFilenm;
        @Schema(description = "이미지경로")
        private String mnuImgpath;
        @Schema(description = "커스텀태그")
        private String mnuCustom;
        @Schema(description = "데스크탑버전 사용여부")
        private String mnuDesktop;
        @Schema(description = "모바일버전 사용여부")
        private String mnuMobile;
        @Schema(description = "정렬순서")
        private String mnuOrder;
        @Schema(description = "인증구분코드")
        private String mnuCertType;
        @Schema(description = "외부연결여부 0,1")
        private String mnuExtnConnYn;
        @Schema(description = "사용시작시")
        private String mnuStartHour;
        @Schema(description = "사용종료시")
        private String mnuEndHour;
        @Schema(description = "등록일자")
        private String regdate;
        @Schema(description = "등록자")
        private String reguserno;
        @Schema(description = "수정일자")
        private String chgdate;
        @Schema(description = "수정자")
        private String chguserno;
        @Schema(description = "삭제여부")
        private String deleteflag;

}

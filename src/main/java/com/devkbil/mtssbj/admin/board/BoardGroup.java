package com.devkbil.mtssbj.admin.board;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.Timestamp;

@ApiModel(value = "게시판 그룹 : BoardGroupVO", description = "게시판 그룹")
@XmlRootElement(name = "BoardGroupVO")
@XmlType(propOrder = {"bgno", "bgname", "bglevel", "bgparent", "bgdeleteflag", "bgused", "bgreply", "bgreadonly", "bgregdate","bgupdate", "bgnotice"})
@Data
@Entity
@Table(name="tbl_boardgroup")
public class BoardGroup {
    
    @ApiModelProperty(value = "게시글 그룹번호")
    @Id
    @Column(name="bgno")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long bgno;
    
    @ApiModelProperty(value = "게시글 그룹명")
    private String bgname;
    @ApiModelProperty(value = "게시글 레벨")
    private String bglevel;
    @ApiModelProperty(value = "게시판 그룹 부모")
    private String bgparent;
    @ApiModelProperty(value = "삭제 여부")
    private String bgdeleteflag;
    @ApiModelProperty(value = "사용여부")
    private String bgused;
    @ApiModelProperty(value = "댓글 사용여부")
    private String bgreply;
    @ApiModelProperty(value = "읽기전용 여부")
    private String bgreadonly;
    
    @ApiModelProperty(value = "생성일자")
    @CreationTimestamp
    private Timestamp bgregdate;
    
    @ApiModelProperty(value = "수정일자")
    @UpdateTimestamp
    private Timestamp  bgupdate;
   
    @ApiModelProperty(value = "공지쓰기 가능여부")
    private String bgnotice;
    
}
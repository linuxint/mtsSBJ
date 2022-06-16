package com.devkbil.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@ApiModel(value = "트리구조 : TreeVO", description = "TreeVO")
@XmlRootElement(name = "TreeVO")
@XmlType(propOrder = {"key", "title", "parent", "isFolder", "children"})
@Getter
@Setter
public class TreeVO {
    
    @ApiModelProperty(value = "트리노드키")
    private String key;
    @ApiModelProperty(value = "트리노드명")
    private String title;
    @ApiModelProperty(value = "상위노트키")
    private String parent;
    @ApiModelProperty(value = "노트속성:폴더여부")
    private boolean isfolder;
    @ApiModelProperty(value = "하위 노트 목록")
    private List children;
    
}
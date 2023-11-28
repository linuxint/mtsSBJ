package com.devkbil.mtssbj.common.tree;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "트리구조 : TreeVO")
@XmlRootElement(name = "TreeVO")
@XmlType(propOrder = {"key", "title", "parent", "isfolder", "children"})
@Getter
@Setter
public class TreeVO {

    @Schema(description = "트리노드키")
    private String key;
    @Schema(description = "트리노드명")
    private String title;
    @Schema(description = "상위노트키")
    private String parent;
    @Schema(description = "노트속성:폴더여부")
    private boolean isfolder;
    @Schema(description = "하위 노트 목록")
    private List children;

}

package com.devkbil.mtssbj.common.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TreeMaker {

    /**
     * 부모코드를 이용하여 계층형 트리 구성.
     */
    public String makeTreeByHierarchy(List<?> listview) {
        List<TreeVO> rootlist = new ArrayList<TreeVO>();

        for (Integer i = 0; i < listview.size(); i++) {
            TreeVO mtDO = (TreeVO)listview.get(i);

            if (mtDO.getParent() == null) {
                rootlist.add(mtDO);
                continue;
            }
            for (Integer j = 0; j < listview.size(); j++) {
                TreeVO ptDO = (TreeVO)listview.get(j);
                if (mtDO.getParent().equals(ptDO.getKey())) {
                    if (ptDO.getChildren() == null) {
                        ptDO.setChildren(new ArrayList<Object>());
                    }
                    List<TreeVO> list = ptDO.getChildren();
                    list.add(mtDO);
                    ptDO.setIsfolder(true);
                    break;
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String str = "";
        try {
            str = mapper.writeValueAsString(rootlist);
        } catch (IOException ex) {
            log.error("TreeMaker");
        }
        return str;
    }
}

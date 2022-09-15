package com.devkbil.mtssbj.datacart;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class DataCartCacheService {
    private final Logger LOGGER = LoggerFactory.getLogger(DataCartCacheService.class);
    
    @Autowired
    public DataCartDao dataCartDao;
    
    private List<Map> dataCart = new ArrayList<Map>();
    
    @PostConstruct
    public void resetDataCartList() throws Exception {
        String userid = "";
        if(dataCart.isEmpty()) {
            synchronized (dataCart) {
                if(dataCart.isEmpty()) {
                    List<Map> MapList;
                    MapList = (ArrayList<Map>) dataCartDao.selectDataCartList(userid);
                    
                    dataCart.clear();
                    dataCart.addAll(MapList);
                    
                }
            }
        }
    }
    
    public void clear() throws Exception {
        dataCart.clear();
    }
    
    public DataCart getDataCartOne(String dataCartId, String inspReqId) throws Exception {
        DataCart dataCartOne = new DataCart();
        Iterator<Map> iterator = dataCart.iterator();
        while (iterator.hasNext()) {
            Map mapOne = (Map) iterator.next();
            if(dataCartId.equals((String)mapOne.get("dataCartId")) && inspReqId.equals((String)mapOne.get("inspReqId")) ) {
                dataCartOne = (DataCart) mapOne;
                break;
            }
        }
        return dataCartOne;
    }
    
    public List<Map> getDataCart() throws Exception {
        return dataCart;
    }
    
    public void setDataCartOne(DataCart dataCartone) throws Exception {
        dataCartDao.insertDataCart(dataCartone); // DB추가
        dataCart.add((Map) dataCartone); // 메모리추가
    }
    
    public void clearDataCartOne(Map map) throws Exception {
        
        String dataCartId = map.get("dataCartId").toString();
        String inspReqId = map.get("inspReqId").toString();
        Iterator<Map> iterator = dataCart.iterator();
        while (iterator.hasNext()) {
            Map mapOne = (Map) iterator.next();
            if(dataCartId.equals((String)mapOne.get("dataCartId")) && inspReqId.equals((String)mapOne.get("inspReqId")) ) {
                dataCartDao.deleteDataCartOne(mapOne); // DB삭제
                mapOne.remove(dataCartId); // 메모리삭제
                break;
            }
        }
    }
    
    public void clearDataCart(String inspReqId) throws Exception {
        dataCartDao.deleteDataCart(inspReqId); // DB삭제
        dataCart.clear(); // 메모리삭제
    }
    
    public int getDataCartCount(String inspReqId) throws Exception {
        return dataCart.size();
    }
    
    public boolean cartCheckout(String inspReqId) throws Exception {
        dataCartDao.cartCheckOut(inspReqId);
        return true;
    }
}

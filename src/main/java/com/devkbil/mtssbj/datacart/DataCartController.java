package com.devkbil.mtssbj.datacart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cart")
public class DataCartController {
    
    String userId = "admin"; // UserProvideService.getUser();
    
    @Autowired
    private DataCartCacheService dataCartCacheService;
    
    @GetMapping
    public List<?> cart() throws Exception {
        
        return dataCartCacheService.getDataCart();
    }
    
    /**
     * 데이터 장바구니 추가
     * @param catgMstDataId
     * @param dataCart
     * @return
     * @throws Exception
     */
    @GetMapping("/add/{catgMstDataId}")
    public List<?> addDataToCart(@PathVariable("catgMstDataId") String catgMstDataId, @RequestBody DataCart dataCart
    ) throws Exception {
        dataCartCacheService.setDataCartOne(dataCart);
        log.debug(String.format("Product with id: %s added to shopping cart.", catgMstDataId));
        return this.cart();
    }
    
    /**
     * 데이터 장바구니 삭제
     * @param dataCartId
     * @param dataCart
     * @return
     * @throws Exception
     */
    @GetMapping("/remove/{dataCartId}")
    public List<?> removeDataFromCart(@PathVariable("dataCartId") String dataCartId, @RequestBody DataCart dataCart) throws Exception {
        
        String catgMstDataId = dataCart.getCatgMstDataId();
        
        Map<String,String> param = new HashMap<String, String>();
        param.put("dataCartId", dataCartId);
        param.put("inspReqId", userId);
        dataCartCacheService.clearDataCartOne(param);
        
        log.debug(String.format("Dasta with id: %s removed from data cart.", catgMstDataId));
    
        return this.cart();
    }
    
    /**
     * 데이터 장바구니 전체삭제
     * @return
     * @throws Exception
     */
    @GetMapping("/clear")
    public List<?> clearProductsInCart() throws Exception {
        dataCartCacheService.clearDataCart(userId);
        return this.cart();
    }
    
    /**
     * 데이터 장바구니 열람신청
     * @return
     * @throws Exception
     */
    @GetMapping("/checkout")
    public List<?> cartCheckout() throws Exception {
        //dataCartService.cartCheckout();
        Boolean retValue = dataCartCacheService.cartCheckout(userId);
        return this.cart();
    }
}

package com.devkbil.datacart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataCartController {
    private static final Logger logger = LoggerFactory.getLogger(DataCartController.class);
    
    String userId = "admin"; // UserProvideService.getUser();
    
    @Autowired
    private DataCartCacheService dataCartCacheService;
    
    @GetMapping("/cart")
    public List<?> cart() throws Exception {
        
        return dataCartCacheService.getDataCart();
    }
    
    @GetMapping("/cart/add/{catgMstDataId}")
    public String addDataToCart(@PathVariable("catgMstDataId") String catgMstDataId, @RequestBody DataCart dataCart
    ) throws Exception {
        dataCartCacheService.setDataCartOne(dataCart);
        logger.debug(String.format("Product with id: %s added to shopping cart.", catgMstDataId));
        return "redirect:/";
    }
    
    @GetMapping("/cart/remove/{dataCartId}")
    public String removeDataFromCart(@PathVariable("dataCartId") String dataCartId, @RequestBody DataCart dataCart) throws Exception {
        
        String catgMstDataId = dataCart.getCatgMstDataId();
        
        Map<String,String> param = new HashMap<String, String>();
        param.put("dataCartId", dataCartId);
        param.put("inspReqId", userId);
        dataCartCacheService.clearDataCartOne(param);
        
        logger.debug(String.format("Dasta with id: %s removed from data cart.", catgMstDataId));
        
        return "redirect:/cart";
    }
    
    @GetMapping("/cart/clear")
    public String clearProductsInCart() throws Exception {
        dataCartCacheService.clearDataCart(userId);
        return "redirect:/cart";
    }
    
    @GetMapping("/cart/checkout")
    public String cartCheckout() throws Exception {
        //dataCartService.cartCheckout();
        Boolean retValue = dataCartCacheService.cartCheckout(userId);
        return "redirect:/cart";
    }
}

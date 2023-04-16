package midtermjava.application.controller;


import midtermjava.application.service.ShoesCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class ShoesCartController {
    @Autowired
    private ShoesCartService shoesCartService;
    @PutMapping("/addCart")
    @ResponseBody
    public HashMap<String, Object> addCartItem (@RequestParam HashMap<String, String> cartInfo) {
        return shoesCartService.addShoeToCart(cartInfo);
    }

    @GetMapping("/getCart")
    @ResponseBody
    public HashMap<String, Object> getCart(@RequestParam("status") String status){
        return shoesCartService.getShoeByStatus(status);
    }

    @PutMapping("/purchaseCart")
    @ResponseBody
    public HashMap<String, Object> purchaseCart(@RequestParam("cartId") String cartId){
        return shoesCartService.purchaseCart(cartId);
    }

    @PutMapping("/addNewCart")
    @ResponseBody
    public HashMap<String, Object> addNewCart(@RequestParam HashMap<String, String> newCart){
        return shoesCartService.addNewCart(newCart);
    }



    @DeleteMapping("/deleteCartDetail")
    @ResponseBody
    public HashMap<String, Object> deleteCartDetail(@RequestParam HashMap<String, String> deleteParams){
        return shoesCartService.deleteCartItem(deleteParams);
    }
}

package midtermjava.application.service;

import midtermjava.application.entity.Shoes;
import midtermjava.application.entity.ShoesCart;
import midtermjava.application.entity.ShoesDetail;
import midtermjava.application.repository.ShoesCartRepository;
import midtermjava.application.repository.ShoesDetailRepository;
import midtermjava.application.repository.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
@Service

public class ShoesCartServiceImpl implements ShoesCartService {
    @Autowired
    private ShoesCartRepository ShoescartRepository;
    @Autowired
    private ShoesDetailRepository ShoesDetailRepository;
    @Autowired
    private ShoesRepository ShoesRepository;

    public HashMap<String, Object> addNewCart(HashMap<String, String> params){
        HashMap<String, Object> response = new HashMap<>();
        if(params.get("id") == null ){
            response.put("mes", "error");
            return response;
        }
        ShoesCart cart = new ShoesCart();
        Shoes shoes = ShoesRepository.findShoesById(Long.parseLong(params.get("id")));
        Long price = Long.valueOf(shoes.getPrice());
        cart.setStatus(0);
        cart.setPrice(price);
        ShoescartRepository.save(cart);
        ShoesDetail detail = new ShoesDetail();
        detail.setShoesCart(cart);

        detail.setShoes(shoes);
        ShoesDetailRepository.save(detail);
        response.put("mes", "success");
        return response;
    }

    public HashMap<String, Object> addShoeToCart(HashMap<String, String> params){
        HashMap<String, Object> response = new HashMap<>();
        if(params.get("shoeId") == null || params.get("cartId") == null){
            response.put("mes", "error");
            return response;
        }
        ShoesCart cart = ShoescartRepository.findShoesCartById(Long.parseLong(params.get("cartId")));
        Shoes shoe = ShoesRepository.findShoesById(Long.parseLong(params.get("shoeId")));
        Long price = cart.getPrice() + shoe.getPrice() ;
        cart.setPrice(price);
        ShoescartRepository.save(cart);
        ShoesDetail detail = new ShoesDetail();
        detail.setShoesCart(cart);
        detail.setShoes(shoe);
        ShoesDetailRepository.save(detail);
        return getShoeByStatus("0");
    }

    public HashMap<String, Object> deleteCartItem(HashMap<String, String> params){
        HashMap<String, Object> response = new HashMap<>();
        if(params.get("cartId") == null || params.get("shoeId") == null){
            response.put("mes", "error");
            return response;
        }
        ShoesCart cart = ShoescartRepository.findShoesCartById(Long.parseLong(params.get("cartId")));
        cart.setPrice(cart.getPrice() - ShoesRepository.findShoesById(Long.parseLong(params.get("shoeId"))).getPrice());
        Shoes shoe = ShoesRepository.findShoesById(Long.parseLong(params.get("shoeId")));
        ShoesDetail detail = ShoesDetailRepository.findShoesDetailByShoesCartAndShoes(cart, shoe);
        if (detail == null) {
            response.put("mes", cart);
            return response;
        }
        ShoescartRepository.save(cart);
        ShoesDetailRepository.deleteById(detail.getId());
        return getShoeByStatus("0");
    }

    public HashMap<String, Object> getShoeByStatus(String statusParam){
        HashMap<String, Object> response = new HashMap<>();
        if(statusParam == null){
            response.put("mes", "error");
            return response;
        }
        List<ShoesCart> cart;
        Integer status = Integer.valueOf(statusParam);
        if(status == 0){
            cart = ShoescartRepository.findShoesCartByStatus(0L);
            response.put("data", cart);
            return response;
        }else if(status == 1){
            cart = ShoescartRepository.findShoesCartByStatus(1L);
            response.put("data", cart);
            return response;
        }
        return null;
    }

    public HashMap<String, Object> purchaseCart(String cartParams){
        HashMap<String, Object> response = new HashMap<>();
        if(cartParams == null){
            response.put("mes", "error");
            return response;
        }
        List<ShoesCart> cart = ShoescartRepository.findShoesCartByStatus(0L);
        cart.get(0).setStatus(1);
        ShoescartRepository.save(cart.get(0));
        response.put("mes", "Purchase success");
        return response;
    }
}

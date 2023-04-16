package midtermjava.application.service;

import java.util.HashMap;

public interface ShoesCartService  {
    public HashMap<String, Object> addNewCart(HashMap<String, String> params);
    public HashMap<String, Object> addShoeToCart(HashMap<String, String> params);
    public HashMap<String, Object> deleteCartItem(HashMap<String, String> params);
    public HashMap<String, Object> getShoeByStatus(String statusParam);
    public HashMap<String, Object> purchaseCart(String cartParams);
}

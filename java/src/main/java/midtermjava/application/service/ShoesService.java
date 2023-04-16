package midtermjava.application.service;

import midtermjava.application.entity.Shoes;

import java.util.HashMap;

public interface ShoesService {
    public Iterable<Shoes> listAllShoes();
    public Shoes getShoesById(Long id);
//    deleteShoes
    public void deleteShoes(Long id);
    public Shoes saveShoes(Shoes shoes);
    Iterable<Shoes> getSearchShoes(String search);

    Iterable<Shoes> getFilterShoes(HashMap <String,String> parmas);
}

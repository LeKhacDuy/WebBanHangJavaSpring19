package midtermjava.application.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import midtermjava.application.entity.Shoes;
import midtermjava.application.repository.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ShoesServiceImpl implements ShoesService {
    @Autowired
    private ShoesRepository shoesRepository;
    @Autowired
    EntityManager entityManager;

    @Override
    public Iterable<Shoes> listAllShoes() {
        return shoesRepository.findAll();
    }

    @Override
    public Shoes getShoesById(Long id) {
        return shoesRepository.findById(id).get();
    }

    @Override
    public void deleteShoes(Long id) {

    }

    @Override
    public Shoes saveShoes(Shoes shoes) {
        return null;
    }
    public Iterable<Shoes> getSearchShoes( String search){
        return shoesRepository.findByNameContainingIgnoreCase(search);
    }

    public Iterable<Shoes> getFilterShoes(HashMap <String,String> params){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shoes> cq = cb.createQuery(Shoes.class);
        Root<Shoes> root = cq.from(Shoes.class);
        List<Predicate> predicates = new ArrayList<>();

        if (params.get("money") != null || params.get("money").equals("")) {
            Long minPrice ;
            Long maxPrice ;
            if(Integer.parseInt(params.get("money"))  == 0) {
                minPrice=0L;
                maxPrice=49L;

            }
            else if(Integer.parseInt(params.get("money"))  == 1){
                minPrice=50L;
                maxPrice=1000000L;
            }
            else {
                minPrice=0L;
                maxPrice=1000000L;
            }

            predicates.add(cb.between(root.get("price"), minPrice, maxPrice));

        }
        if(params.get("type")!=null || params.get("type").equals("")){
            predicates.add(cb.like(root.get("type"),"%"+params.get("type")+"%"));
        }
        if(params.get("brand")!=null || params.get("brand").equals("")){
            predicates.add(cb.like(root.get("brand"),"%"+params.get("brand")+"%"));
        }
        if(params.get("color")!=null || params.get("color").equals("")){
            predicates.add(cb.like(root.get("color"),"%"+params.get("color")+"%"));
        }
        if(params.get("name")!=null || params.get("name").equals("")){
            predicates.add(cb.like(root.get("name"),"%"+params.get("name")+"%"));
        }
        if(params.get("sort") != null || params.get("sort").equals("")){
            if(params.get("sort").equals("asc") ){
                cq.orderBy(cb.asc(root.get("price")));
            }else{
                cq.orderBy(cb.desc(root.get("price")));
            }
        }


        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Shoes> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

}

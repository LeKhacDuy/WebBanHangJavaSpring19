package midtermjava.application.repository;

import midtermjava.application.entity.Shoes;

import midtermjava.application.entity.ShoesCart;
import midtermjava.application.entity.ShoesDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ShoesDetailRepository extends CrudRepository<ShoesDetail, Long> {

     ShoesDetail findShoesDetailByShoesCartAndShoes(ShoesCart cart, Shoes shoes);
     @Transactional
     void deleteById(Long id);
}

package midtermjava.application.repository;

import midtermjava.application.entity.ShoesCart;
import midtermjava.application.entity.ShoesDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface ShoesCartRepository extends CrudRepository<ShoesCart, Long> {


    List<ShoesCart> findShoesCartByStatus(Long i);

    ShoesCart findShoesCartById(Long cartId);


    
}

package midtermjava.application.repository;

import midtermjava.application.entity.Shoes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ShoesRepository extends CrudRepository<Shoes,Long> {
    Iterable<Shoes> findByNameContainingIgnoreCase(String search);

    Shoes findShoesById(long id);
}

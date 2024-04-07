package proj.task5.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proj.task5.entity.TppProduct;

//@Repository
public interface TppProductRepo extends CrudRepository<TppProduct, Long> {
    TppProduct findFirstByNumber(String number);

}

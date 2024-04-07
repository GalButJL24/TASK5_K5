package proj.task5.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proj.task5.entity.TppProductRegister;

import java.util.List;
@Repository
public interface TppProductRegisterRepo extends CrudRepository<TppProductRegister, Long> {
    //   @Query(value = "select t from tpp_product_register t where t.product_id = ?1 and t.type = ?2", nativeQuery = true)
    //   public List<Tpp_product_register> findPr(Integer product_id, String type);
    public List<TppProductRegister> findByproductId(Long productId);
}

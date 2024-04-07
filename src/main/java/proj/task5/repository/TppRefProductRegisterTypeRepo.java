package proj.task5.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proj.task5.entity.TppRefProductRegisterType;

import java.util.List;
@Repository
public interface TppRefProductRegisterTypeRepo extends CrudRepository<TppRefProductRegisterType, Long> {
    List<TppRefProductRegisterType> findByValue(String value);
}

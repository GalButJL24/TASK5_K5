package proj.task5.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proj.task5.entity.TppRefAccountType;

@Repository
public interface TppRefAccountTypeRepo extends CrudRepository<TppRefAccountType, Long> {
    public  TppRefAccountType findByValue(String value);
}

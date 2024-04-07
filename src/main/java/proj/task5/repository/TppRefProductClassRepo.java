package proj.task5.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proj.task5.entity.TppRefProductClass;

import java.util.List;

@Repository
public interface TppRefProductClassRepo extends CrudRepository<TppRefProductClass, Long> {
    public List<TppRefProductClass> findByValue(String value);
}

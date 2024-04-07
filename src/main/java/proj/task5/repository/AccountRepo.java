package proj.task5.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proj.task5.entity.Account;
@Repository
public interface AccountRepo extends CrudRepository<Account, Long> {
}

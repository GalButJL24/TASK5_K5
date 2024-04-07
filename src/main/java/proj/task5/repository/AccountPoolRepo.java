package proj.task5.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proj.task5.entity.AccountPool;

@Repository
public interface AccountPoolRepo extends CrudRepository<AccountPool, Long> {
    //  Слишком длинно Можно было делать как  Query (оставила, чтобы убедиться, что так тоже можно)
    AccountPool findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(String branchCode
            , String CurrencyCode
            , String MdmCode
            , String PriorityCode
            , String registryTypeCode);
}
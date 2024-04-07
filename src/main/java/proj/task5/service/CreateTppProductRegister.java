package proj.task5.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proj.task5.enumState.State;
import proj.task5.Interface.CreateRecordsable;
import proj.task5.productExample.model.ProdExample;
import proj.task5.productRegistr.model.ProdRegistr;
import proj.task5.repository.AccountPoolRepo;
import proj.task5.repository.TppProductRegisterRepo;
import proj.task5.repository.TppRefProductClassRepo;
import proj.task5.entity.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@Qualifier("TppProductRegister") //  Если понадобится определить несколько реализаций одного интерфейса
public class CreateTppProductRegister  implements CreateRecordsable {
    @Autowired
    AccountPoolRepo account_poolRepo;
    @Autowired
    TppProductRegisterRepo tpp_productRegisterRepo;
    @Autowired
    TppRefProductClassRepo tpp_refProductClassRepo;


    private List<TppRefProductRegisterType>     tppTypeLst = new ArrayList<>();
    private TppProduct tpp_product;

    // Выбираем связанные записи из tpp_ref_pproduct_class
    public List<TppRefProductRegisterType> getLstType(String product_code){
        List<TppRefProductRegisterType> tppTypeList = new ArrayList<>();
        List<TppRefProductClass>  tppRefProductClassLst;
        tppRefProductClassLst = tpp_refProductClassRepo.findByValue(product_code); //
        for (TppRefProductClass tpcl : tppRefProductClassLst) {
            // Находим связанные записи Tpp_ref_product_register_type(нас интересуют все с account_type = "Клиентский")
            List<TppRefProductRegisterType> tppProdRegTypeLst = tpcl.getTpp_refProductRegisterTypes();
            // среди найденных отбираем с Account_type = "Клиентский"
            for (TppRefProductRegisterType type : tppProdRegTypeLst) {
                if (type.getAccount_type().getValue().equals("Клиентский"))
                    tppTypeList.add(type);
            }
        }
        tppTypeLst = tppTypeList;
        return  tppTypeLst;
    }

    // Добавляем запись в таблицу tpp_product_register
    @Override
    public <T, K>  T  create_rec_table(K model){
        ProdRegistr prodRegistr = (ProdRegistr) model;
        TppProductRegister tpp_productRegister = new TppProductRegister();
        AccountPool account_pool = account_poolRepo.findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
                prodRegistr.getBranchCode()             // branch_code
                , prodRegistr.getCurrencyCode()          //currency_code
                , prodRegistr.getMdmCode()               //mdm_code
                , prodRegistr.getPriorityCode()          //priority_code
                , prodRegistr.getRegistryTypeCode()      //registry_type_code
        );
        tpp_productRegister.setProductId(prodRegistr.getInstanceId());
        tpp_productRegister.setType(prodRegistr.getRegistryTypeCode());
        tpp_productRegister.setCurrency_code(prodRegistr.getCurrencyCode());
        tpp_productRegister.setState(State.OPEN.getName());

        // Номер счета берется первый (заполняем инфо по счетам)
        if (!(account_pool == null)) {

            List<Account> accountLst = account_pool.getAccountList();
            Account account = accountLst.get(0);
            tpp_productRegister.setAccount(account.getId());
            tpp_productRegister.setAccountNumber(account.getAccount_number());
        }

        TppProductRegister tpp_productRegisterSave = tpp_productRegisterRepo.save(tpp_productRegister);
        //System.out.println("Добавлена запись в Tpp_product_register");
        return  (T) tpp_productRegisterSave;
    }

    // Добавляем несколько записей в табицу tpp_product_register

    @Override
    public<T, K> List<T> create_recs_table(K model){
        ProdExample prodExample = (ProdExample) model;
        List<TppProductRegister> prodRegLst = new ArrayList<>();

        tppTypeLst = getLstType(prodExample.getProductCode());
        for (TppRefProductRegisterType typeLst : tppTypeLst) {

            ProdRegistr prodRegistr = new ProdRegistr();
            prodRegistr.setInstanceId(tpp_product.getId());
            prodRegistr.setBranchCode(prodExample.getBranchCode());
            prodRegistr.setCurrencyCode(prodExample.getIsoCurrencyCode());
            prodRegistr.setMdmCode(prodExample.getMdmCode());
            prodRegistr.setPriorityCode(prodExample.getUrgencyCode());
            prodRegistr.setRegistryTypeCode(typeLst.getValue());
            // Добавляем записи
            TppProductRegister tpp_productRegister = create_rec_table(prodRegistr);
            // System.out.println("Добавлена запись");
            prodRegLst.add(tpp_productRegister);
        }
        return (List<T>) prodRegLst;
    }

    public List<TppProductRegister> create_recs_table_product_register(ProdExample prodExample
            , TppProduct tpp_product){
        this.tpp_product = tpp_product;
        return create_recs_table(prodExample);
    }


}

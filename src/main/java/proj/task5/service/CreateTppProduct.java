package proj.task5.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proj.task5.Interface.CreateRecordsable;
import proj.task5.productExample.model.ProdExample;
import proj.task5.repository.TppProductRepo;
import proj.task5.repository.TppRefProductClassRepo;
import proj.task5.entity.TppProduct;
import proj.task5.entity.TppRefProductClass;


import java.util.List;

@Data
@Service
@Qualifier("TppProduct") //  Если понадобится определить несколько реализаций одного интерфейса
public class CreateTppProduct implements CreateRecordsable {
    @Autowired
    TppProductRepo tpp_productRepo;
    @Autowired
    TppRefProductClassRepo tpp_refProductClassRepo;

    @Override
    public <T, K>  T  create_rec_table(K model){
        ProdExample  prodExample = (ProdExample) model;
        TppProduct tpp_product = new TppProduct();
        List<TppRefProductClass> tppRefProductClassLst = tpp_refProductClassRepo.findByValue(prodExample.getProductCode());
        if (!(tppRefProductClassLst == null))
            tpp_product.setProduct_code_id(tppRefProductClassLst.get(0).getInternal_id());

        tpp_product.setClient_id(Long.valueOf(prodExample.getMdmCode()));
        tpp_product.setNumber(prodExample.getContractNumber());
        tpp_product.setPriority(Long.valueOf(prodExample.getPriority()));
        tpp_product.setType(prodExample.getProductType());
        tpp_product.setDate_of_conclusion(prodExample.getContractDate().atStartOfDay());

        tpp_product.setPenalty_rate(prodExample.getInterestRatePenalty());
        tpp_product.setThreshold_amount(prodExample.getThresholdAmount());
        tpp_product.setTax_rate(prodExample.getTaxPercentageRate());
        TppProduct tpp_productSave =  tpp_productRepo.save(tpp_product);
        //System.out.println("Добавлена запись в Tpp_product");
        return (T) tpp_productSave;

    }
    @Override
    public <T, K> List<T> create_recs_table(K model){
        // возможна реализация в будущем
        return null;
    }


}

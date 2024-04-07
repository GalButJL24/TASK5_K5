package proj.task5.productRegistr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proj.task5.exceptions.NotFoundReqException;
import proj.task5.Interface.StepPRegisterExecable;
import proj.task5.productRegistr.model.ProdRegistr;
import proj.task5.entity.TppRefProductRegisterType;
import proj.task5.repository.TppRefProductRegisterTypeRepo;


import java.util.List;

@Service
@Qualifier("3_PR") //  Если понадобится определить несколько реализаций одного интерфейса
public class Step3R implements StepPRegisterExecable {
    @Autowired
    private TppRefProductRegisterTypeRepo tpp_refProductRegisterTypeRepo;


    @Override
    public void execute(ProdRegistr modelRegister){
        //  Шаг 3 Наличие записи в tpp_ref_product_register_type
        List<TppRefProductRegisterType> tpp_type =  tpp_refProductRegisterTypeRepo.findByValue(modelRegister.getRegistryTypeCode());
        if (tpp_type.isEmpty())
            throw new NotFoundReqException("Код продукта  " + modelRegister.getInstanceId() +
                    " значение " + modelRegister.getRegistryTypeCode() +
                    " не найдено в Каталоге продуктов tpp_ref_product_regisre_type" +
                    " для данного типа Регистра" );
        // System.out.println("STEP_3_PR"); // Оставлено Отладка

    }


}

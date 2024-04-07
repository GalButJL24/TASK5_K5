package proj.task5.productRegistr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proj.task5.exceptions.BadReqException;
import proj.task5.Interface.StepPRegisterExecable;
import proj.task5.productRegistr.model.ProdRegistr;
import proj.task5.entity.TppProductRegister;
import proj.task5.repository.TppProductRegisterRepo;


import java.util.List;

@Service
@Qualifier("2_PR") //  Если понадобится определить несколько реализаций одного интерфейса
public class Step2R implements StepPRegisterExecable {
    @Autowired
    private TppProductRegisterRepo tppProductRegisterRepo;

    private boolean foundRepeat(Long product_id, String type){
        List<TppProductRegister> tpp_productRegisters = tppProductRegisterRepo.findByproductId(product_id);
        return tpp_productRegisters.stream().anyMatch(x->x.getType().equals(type));
    }


    @Override
    public void  execute(ProdRegistr modelProduct) {
        // Шаг 2 Проверка на дубли(таблица tpp_product_register)
        if (foundRepeat(modelProduct.getInstanceId(), modelProduct.getRegistryTypeCode()))
            throw  new BadReqException("Параметр registryTypeCode тип регистра " +
                    modelProduct.getRegistryTypeCode() +
                    " уже существует для ЭП  с ИД " + modelProduct.getInstanceId());

        // System.out.println("STEP_2_PR"); // Оставлено Отладка
    }


}

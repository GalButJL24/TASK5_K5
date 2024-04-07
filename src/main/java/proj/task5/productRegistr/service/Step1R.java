package proj.task5.productRegistr.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proj.task5.exceptions.BadReqException;
import proj.task5.Interface.StepPRegisterExecable;
import proj.task5.productRegistr.model.ProdRegistr;

@Service
@Qualifier("1_PR") //  Если понадобится определить несколько реализаций одного интерфейса
public class Step1R implements StepPRegisterExecable {

    @Override
    public void  execute(ProdRegistr modelProduct){
        // Шаг 1 Проверка на обязательность
        if (modelProduct.getInstanceId() == null) {
            throw new BadReqException("Идентификатор экземпляра продукта <InstanceId>  не заполнен");
        }
        //System.out.println("STEP_1_PR"); // Оставлено Отладка
    }



}

package proj.task5.productExample.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proj.task5.Interface.StepPExampleExecable;
import proj.task5.exceptions.NotFoundReqException;
import proj.task5.productExample.model.ProdExample;

import proj.task5.entity.TppRefProductRegisterType;

import proj.task5.service.CreateTppProductRegister;


import java.util.List;
// Найти связанные записи( если не нашли отправить статус NOT_FOUND Шаг 1.3 ТЗ)
@Data
@Service
@Qualifier("13_PE") //  Если понадобится определить несколько реализаций одного интерфейса
public class Step13E implements StepPExampleExecable {

    @Autowired
    private CreateTppProductRegister createTppProductRegister;

    @Override
    public ResponseEntity<?> execute(ProdExample prodExample) {
        // Выбираем записи из tpp_ref_pproduct_class (чтобы затем найти все записи из  tpp_ref_product_register_type)
        List<TppRefProductRegisterType>   tppTypeLst =  createTppProductRegister.getLstType(prodExample.getProductCode());
        // Если не нашли вернем ответ
        if (tppTypeLst.isEmpty())
            throw new NotFoundReqException("Код продукта  " + prodExample.getProductCode() +
                    " не найден в каталоге продуктов tpp_ref_product_class");

        //System.out.println("Step_13_PE"); // Оставлено Отладка
        // Если все поля заполнены позволяем выполнять функционал далее
        return null;
    }
}

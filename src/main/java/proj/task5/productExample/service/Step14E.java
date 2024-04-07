package proj.task5.productExample.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.task5.Interface.StepPExampleExecable;
import proj.task5.productExample.model.ProdExample;
import proj.task5.entity.Agreement;
import proj.task5.entity.TppProduct;
import proj.task5.entity.TppProductRegister;
import proj.task5.service.CreateTppProduct;
import proj.task5.service.CreateTppProductRegister;

import java.util.List;


@Service
@Data
@Qualifier("14_PE")
public class Step14E implements StepPExampleExecable {
    // ЭП

    @Autowired
    @Qualifier("TppProduct")
    private CreateTppProduct createTppProduct;
    @Autowired
    @Qualifier("TppProductRegister")
    private CreateTppProductRegister createTppProductRegister;


    TppProduct tppProduct;
    List<Agreement> agrList;
    List<TppProductRegister> tppProductRegisters;

    @Transactional
    public  void create_records_tpp(ProdExample modelProdExample){
        // создаем запись в Tpp_product
        tppProduct = createTppProduct.create_rec_table(modelProdExample);
        // Добавим записи в таблицу Tpp_product_register
        tppProductRegisters =  createTppProductRegister.create_recs_table_product_register(modelProdExample, tppProduct);
        // Добавим записи в таблицу Agreement (в  ТЗ не указано добавлять пока пропускаю)
        //createAgreement.create_recs_table_agreement(modelProdExample, tpp_product);
        agrList = tppProduct.getAgreementList();
    }


    @Override
    public Object execute(ProdExample prodExample) {
        // создадим записи в Tpp_product, Tpp_product_register
        create_records_tpp(prodExample);
        // Сформируем ответ
        StructOkAnswer okAnswer = new StructOkAnswer();
        // Заполним  ответ Ok
        okAnswer.setFields(tppProduct.getId(),agrList, tppProductRegisters);
        //System.out.println("Step_14_PE");
        return okAnswer;
    }
}

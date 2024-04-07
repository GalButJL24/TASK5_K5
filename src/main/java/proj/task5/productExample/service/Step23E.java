package proj.task5.productExample.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.task5.Interface.StepPExampleExecable;
import proj.task5.productExample.model.ProdExample;
import proj.task5.repository.TppProductRepo;
import proj.task5.repository.TppProductRegisterRepo;
import proj.task5.entity.Agreement;
import proj.task5.entity.TppProduct;
import proj.task5.entity.TppProductRegister;
import proj.task5.service.CreateAgreement;

import java.util.List;

@Data
@Service
@Qualifier("23_PE")
public class Step23E implements StepPExampleExecable {


    @Autowired
    @Qualifier("Agreement")
    private CreateAgreement createAgreement;


    @Autowired
    private TppProductRegisterRepo tppProductRegisterRepo;

    @Autowired
    private TppProductRepo tpp_productRepo;

    private TppProduct tppProduct;
    private List<Agreement> tppAgrLst;
    private List<TppProductRegister> tppRegLst;

    @Transactional
    public  void create_records_agreement(ProdExample modelProdExample){
        tppProduct = tpp_productRepo.findById(modelProdExample.getInstanceId()).orElse(null);
        // Добавим записи в таблицу Agreement
        tppAgrLst= createAgreement.create_recs_table_agreement(modelProdExample, tppProduct);
        // будем выводить все agreement по product_id
        tppAgrLst = createAgreement.findAllAgreement(tppProduct);
        // Найлем записи в реестре платежей для формирования ответа
        tppRegLst = tppProductRegisterRepo.findByproductId(tppProduct.getId());
    }


    @Override
    public Object execute(ProdExample prodExample) {
        create_records_agreement(prodExample);
        // Сформируем ответ
        StructOkAnswer okAnswer = new StructOkAnswer();
        // Заполним  ответ Ok
        okAnswer.setFields(tppProduct.getId(),tppAgrLst,tppRegLst);
        //System.out.println("Step_23_PE");
        return  okAnswer;

    }
}

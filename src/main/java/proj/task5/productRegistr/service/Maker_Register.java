package proj.task5.productRegistr.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proj.task5.Interface.StepPRegisterExecable;
import proj.task5.productRegistr.model.ProdRegistr;
import proj.task5.entity.TppProductRegister;
import proj.task5.service.CreateTppProductRegister;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class Maker_Register {
    private List<StepPRegisterExecable> stepLst;
    private ProdRegistr modelRegistr;
    @Autowired
    @Qualifier("TppProductRegister")
    CreateTppProductRegister createTppProductRegister;
    @Autowired
    public Maker_Register(@Qualifier("1_PR") Step1R step_1
            , @Qualifier("2_PR") Step2R step_2
            , @Qualifier("3_PR") Step3R step_3
    ) {
        stepLst = List.of(step_1, step_2, step_3);

    }


    Object  createAnswerOk(Long  reg_id) {
        Map<String, Map<String, String>> mp = new HashMap<>();
        mp.put("data",  new HashMap<String, String>() {{
            put("accountId", reg_id.toString());}});
        return  mp;
    }

    public Object  execute(){
        for (StepPRegisterExecable step : stepLst) {
            step.execute(modelRegistr);
        }
        // если добрались, то делаем запись в регистр
        TppProductRegister tpp_productRegister = createTppProductRegister.create_rec_table(modelRegistr);
        // возвращаем статус
        return createAnswerOk(tpp_productRegister.getId());

    }




}

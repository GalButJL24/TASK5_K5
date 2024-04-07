package proj.task5.productExample.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import proj.task5.Interface.StepPExampleExecable;

import proj.task5.productExample.model.ProdExample;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Создание ЭП
@Data
@Service
public class Maker_Example {
    // Класс для парсинга JSON
    private ProdExample modelProdExample;

    // Если не заполнен InstanceId определяем логику выполнения
    private List<StepPExampleExecable> stepLstNullInstance = new ArrayList<>();
    // Если заполнен InstanceId определяем логику выполнения
    private List<StepPExampleExecable> stepLstNotNullInstance = new ArrayList<>();
   // public Maker_PExample() {}
    // Внедряемые бины Не совсем нравится конструктор: можно было наверное проще как-то(много параметров)
   // private final Step_11_PE step_11;
    @Autowired
    public Maker_Example(@Qualifier("1_PE") Step1E step_1
            , @Qualifier("11_PE") Step11E step_11
            , @Qualifier("12_PE") Step12E step_12
            , @Qualifier("13_PE") Step13E step_13
            , @Qualifier("14_PE") Step14E step_14
            , @Qualifier("21_PE") Step21E step_21
            , @Qualifier("23_PE") Step23E step_23
    ) {
      // this.step_11 = step_11;
        stepLstNullInstance = List.of(step_1, step_11, step_12, step_13, step_14); // Если НЕ задан InstanceId
        stepLstNotNullInstance = List.of(step_1, step_21, step_12, step_23); // Если Задан InstanceId

    }

    Object CreateAnswerOk(StructOkAnswer structOkAnswer) {
        Map<String,  StructOkAnswer> mp = new HashMap<>();
        mp.put("data", structOkAnswer);
        return mp;
    }
    public Object execute(){
        List<StepPExampleExecable>   listExecArr;
        // Если НЕ задан InstanceId
        if (modelProdExample.getInstanceId() == null)
            listExecArr = new ArrayList<>(stepLstNullInstance);
        else
            listExecArr = new ArrayList<>(stepLstNotNullInstance);

        //Выполняем  шаги последовательно
        Object  resp = null;
        for (StepPExampleExecable step : listExecArr) {
            resp = step.execute(modelProdExample);
        }
        // если дошли и не свалились
        if (!(resp == null))
            return CreateAnswerOk((StructOkAnswer) resp);
        // А вдруг ....
        return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR))
                .body(Map.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Не предвиденная ошибка чтения/записи данных"));

    }


}

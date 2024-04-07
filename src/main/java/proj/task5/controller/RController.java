package proj.task5.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.task5.productExample.model.ProdExample;
import proj.task5.productExample.service.Maker_Example;
import proj.task5.productRegistr.model.ProdRegistr;
import proj.task5.productRegistr.service.Maker_Register;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/",  produces = MediaType.APPLICATION_JSON_VALUE)
public class RController {
    @Autowired
    Maker_Example createProdExampl;

    @Autowired
    Maker_Register makerRegister;

    // Продуктовый регистр
    @SneakyThrows
    @PostMapping(value = "corporate-settlement-account/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> corporateSettlementAccount(@RequestBody ProdRegistr modelRegister)
    {
        makerRegister.setModelRegistr(modelRegister);
        Object obj = makerRegister.execute();
        return new ResponseEntity<>(obj, new HttpHeaders(), HttpStatus.CREATED);
    }
    //Экземпляр продуктов
    @PostMapping("corporate-settlement-instance/create")
    public ResponseEntity<?> corporateSettlementInstance(@RequestBody ProdExample modelProdExample) {
        createProdExampl.setModelProdExample(modelProdExample);
        Object obj =  createProdExampl.execute();
        return new ResponseEntity<>(obj, new HttpHeaders(), HttpStatus.CREATED);
    }


}

package proj.task5.productExample.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import proj.task5.entity.Agreement;
import proj.task5.entity.TppProductRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StructOkAnswer {
    private String       instanseId;
    private List<String> registerId = new ArrayList<>();
    private List<String> supplementaryAgreementId = new ArrayList<>();
    void setFields(Long tpp_product_id, List<Agreement> agrList, List<TppProductRegister> tpp_productRegisters){
        this.instanseId = tpp_product_id.toString();
        if (!(tpp_productRegisters.isEmpty()))
            this.registerId = tpp_productRegisters.stream().map(x->x.getId().toString()).collect(Collectors.toList());
        if (!(agrList.isEmpty()))
            this.supplementaryAgreementId =  agrList.stream().map(x->x.getId().toString()).collect(Collectors.toList());
    }

}

package proj.task5.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proj.task5.Interface.CreateRecordsable;
import proj.task5.productExample.model.InstanceArrangement;
import proj.task5.productExample.model.ProdExample;
import proj.task5.repository.AgreementRepo;
import proj.task5.entity.Agreement;
import proj.task5.entity.TppProduct;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@Qualifier("Agreement") //  Если понадобится определить несколько реализаций одного интерфейса
public class CreateAgreement implements CreateRecordsable {
    @Autowired
    AgreementRepo agreementRepo;

    TppProduct tppProduct = new TppProduct();
    @Override
    public <T, K>  T  create_rec_table(K model) {
        InstanceArrangement agr = (InstanceArrangement) model;
        Agreement agreement = new Agreement();

        agreement.setProductId(Math.toIntExact(tppProduct.getId()));
        agreement.setGeneral_agreement_id(agr.getGeneralAgreementId());
        agreement.setSupplementary_agreement_id(agr.getSupplementaryAgreementId());
        agreement.setArrangement_type(agr.getArrangementType());
        if (!(agr.getShedulerJobId() == null))
            agreement.setSheduler_job_id(Long.valueOf(agr.getShedulerJobId()));
        agreement.setNumber(agr.getNumber());
        agreement.setOpening_date(agr.getOpeningDate().atStartOfDay());
        if(!(agr.getClosingDate() == null))
            agreement.setClosing_date(agr.getClosingDate().atStartOfDay());
        if(!(agr.getCancelDate() == null))
            agreement.setCancel_date(agr.getCancelDate().atStartOfDay());
        if(!(agr.getValidityDuration() == null))
            agreement.setValidity_duration(Long.valueOf(agr.getValidityDuration()));
        agreement.setCancellation_reason(agr.getCancellationReason());
        agreement.setStatus(agr.getStatus());
        if(!(agr.getInterestCalculationDate() == null))
            agreement.setInterest_calculation_date(agr.getInterestCalculationDate().atStartOfDay());
        agreement.setInterest_rate(agr.getInterestRate());
        agreement.setCoefficient(agr.getCoefficient());
        agreement.setCoefficient_action(agr.getCoefficientAction());
        agreement.setMinimum_interest_rate(agr.getMinimumInterestRate());
        if(!(agr.getMinimumInterestRateCoefficient() == null))
            agreement.setMinimum_interest_rate_coefficient(Double.valueOf(agr.getMinimumInterestRateCoefficient()));
        agreement.setMinimum_interest_rate_coefficient_action(agr.getMinimumInterestRateCoefficientAction());
        agreement.setMaximal_interest_rate(agr.getMaximalInterestRate());
        agreement.setMaximal_interest_rate_coefficient(agr.getMaximalInterestRateCoefficient());
        agreement.setMaximal_interest_rate_coefficient_action(agr.getMaximalInterestRateCoefficientAction());
        Agreement agreementAdd =  agreementRepo.save(agreement);
        //System.out.println("Добавлена запись в Agreement");
        return (T) agreementAdd;
    }

    @Override
    public <T, K> List<T> create_recs_table(K model){
        ProdExample prodExample = (ProdExample) model;
        List<Agreement> agreementLst = new ArrayList<>();
        List<InstanceArrangement> instAgr =  prodExample.getInstanceArrangement();
        for (InstanceArrangement  agr : instAgr) {
            Agreement agreement = create_rec_table(agr);
            agreementLst.add(agreement);
        }
        return  (List<T> ) agreementLst;
    }

    // Дополнительный , вспомогательный функционал
    public List<Agreement> create_recs_table_agreement(ProdExample prodExample, TppProduct tpp_product) {
        this.tppProduct = tpp_product;
        return  create_recs_table(prodExample);
    }

    public List<Agreement> findAllAgreement(TppProduct tpp_product){
        return agreementRepo.findByProductId(Math.toIntExact(tpp_product.getId()));
    }



}

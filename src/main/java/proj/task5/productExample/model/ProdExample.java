package proj.task5.productExample.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
// Формат запроса ЭП
@Data
public class ProdExample {
    private Long instanceId;                       // Id продукта
    @NotBlank
    private String productType;                     // Тип ЭП
    @NotBlank
    private String productCode;                     // Код продукта в каталоге прод-в
    @NotBlank
    private String registerType;                    // Тип регистра
    @NotBlank
    private String mdmCode;                         // Код кл-та(mdm)
    @NotBlank
    private String contractNumber;                  // Номер дог-ра
    @NotNull
    private LocalDate contractDate;                 // Дата закл.дог-ра обсл.
    @NotNull
    private Integer priority;                       // Приоритет
    private Double interestRatePenalty;             // Штрафн. проц. ставка
    private Double minimalBalance;                  // Несниж. остаток
    private Double thresholdAmount;                 // Порог. сумма
    private String accountingDetails;               // Реквизиты выплаты
    private String rateType;                        // Выбор ставки
    private Double taxPercentageRate;               // Ставка налог.
    private Double technicalOverdraftLimitAmount;   // Лимит тех/овер.
    @NotNull
    private Integer contractId;                     // Id договора
    @NotBlank
    private String BranchCode;                      // Код филиала
    @NotBlank
    private String IsoCurrencyCode;                 // Код валюты
    @NotBlank
    private String urgencyCode;                     // Код срочн-ти договора
    private Integer ReferenceCode;                  // Код точки продаж
    private Map<String, List<AdditionalPropertiesVip>> additionalPropertiesVips; // массив доп. признаков
    private List<InstanceArrangement>                  instanceArrangement; // Массив доп. соглашений



}

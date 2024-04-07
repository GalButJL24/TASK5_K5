package proj.task5.productExample.model;

import lombok.Data;
import lombok.NoArgsConstructor;
// Массив дополнительных признаков для сегмента КИБ
@Data
@NoArgsConstructor
public class AdditionalPropertiesVip {
    private String key;
    private String value;
    private String name;
}

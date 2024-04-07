package proj.task5.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="tpp_ref_product_register_type")
public class TppRefProductRegisterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internal_id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "type" , referencedColumnName = "value")
    List<TppProductRegister> tppProductRegisterList = new ArrayList<>();

    private String value;
    private String register_type_name;
    @ManyToOne
    @JoinColumn(name = "product_class_code", referencedColumnName = "value")
    private TppRefProductClass product_class_code;

    private LocalDateTime register_type_start_date;
    private LocalDateTime register_type_end_date;

    @ManyToOne
    @JoinColumn(name= "account_type",  referencedColumnName = "value")
    private TppRefAccountType account_type;

    // private String account_type;
}

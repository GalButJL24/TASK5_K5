package proj.task5.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="tpp_ref_account_type")
public class TppRefAccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internal_id;

    //@Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_type" , referencedColumnName = "value")
    private List<TppRefProductRegisterType> tpp_refProductRegisterTypes = new ArrayList<>();

    private String value;
}
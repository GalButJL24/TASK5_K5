package proj.task5.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "account_pool")
public class AccountPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_pool_id")
    List<Account> accountList = new ArrayList<>();

    @Column(name = "branch_code")
    private String branchCode;
    @Column(name = "currency_code")
    private String currencyCode;
    @Column(name = "mdm_code")
    private String mdmCode;
    @Column(name= "priority_code")
    private String priorityCode;
    @Column(name = "registry_type_code")
    private String registryTypeCode;
}

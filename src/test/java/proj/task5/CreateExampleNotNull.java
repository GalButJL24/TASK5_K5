package proj.task5;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import proj.task5.exceptions.NotFoundReqException;
import proj.task5.productExample.model.InstanceArrangement;
import proj.task5.productExample.model.ProdExample;
import proj.task5.productExample.service.*;
import proj.task5.repository.TppProductRepo;
import proj.task5.entity.TppProduct;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;

@SpringBootTest(classes = {Step21E.class, TppProductRepo.class})
@SpringBootApplication(scanBasePackages = "proj.task5")
public class CreateExampleNotNull {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        modelExample.setInstanceId(null);
        modelExample.setProductType("НСО");
        modelExample.setProductCode("03.012.002");
        modelExample.setRegisterType("03.012.002_47533_ComSoLd");
        modelExample.setMdmCode("15");
        modelExample.setContractNumber("122/FK");
        modelExample.setContractDate(LocalDate.parse("2024-03-29", dateTimeFormatter));
        modelExample.setPriority(Integer.valueOf("00"));
        modelExample.setContractId(11);
        modelExample.setBranchCode("0022");
        modelExample.setIsoCurrencyCode("800");
        modelExample.setUrgencyCode("00");
        List<InstanceArrangement> argLst = new ArrayList<>();

        InstanceArrangement arg = new InstanceArrangement();
        arg.setNumber("124/RT");
        arg.setOpeningDate(LocalDate.parse("2024-03-29", dateTimeFormatter));
        argLst.add(arg);

        arg = new InstanceArrangement();
        arg.setNumber("456/RT");
        arg.setOpeningDate(LocalDate.parse("2024-04-10", dateTimeFormatter));
        argLst.add(arg);

        modelExample.setInstanceArrangement(argLst);

    }
    ProdExample modelExample = new ProdExample();


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.name", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    @Qualifier("21_PE")
    Step21E step_21_pe;

    @Autowired
    @Qualifier("23_PE")
    Step23E step_23_pe;



    @Autowired
    Maker_Example makerPExample;

    @Autowired
    TppProductRepo tpp_productRepo;

    @Test
    @Description("Проверка шага 21  ТЗ")
    void step_PETest(){
        TppProduct tpp_product;
        //tpp_product =tpp_productRepo.findFirst().orElse(null);
        makerPExample.setModelProdExample(modelExample);
        // запись не найдена
        modelExample.setInstanceId(99L);
        assertThrows(NotFoundReqException.class, ()->step_21_pe.execute(modelExample));

        modelExample.setInstanceId(null);

        makerPExample.setModelProdExample(modelExample);
        makerPExample.execute();
        tpp_product = tpp_productRepo.findFirstByNumber(modelExample.getContractNumber());
        Assertions.assertNotNull(tpp_product);

        modelExample.setInstanceId(tpp_product.getId());
        Assertions.assertDoesNotThrow(()->step_21_pe.execute(modelExample));
    }

    @Test
    @Description("Проверка создания записей в Agreement")
    void maker_PExampleTest() {
        modelExample.setContractNumber("122/FF");
        TppProduct tpp_product;
        makerPExample.setModelProdExample(modelExample);
        makerPExample.execute();
        tpp_product = tpp_productRepo.findFirstByNumber(modelExample.getContractNumber());

        Assertions.assertNotNull(tpp_product);

        modelExample.setInstanceId(tpp_product.getId());

        StructOkAnswer responce =   (StructOkAnswer) step_23_pe.execute(modelExample);

        Assertions.assertNotNull(responce);
        Assertions.assertNotNull(responce.getInstanseId());

    }

}

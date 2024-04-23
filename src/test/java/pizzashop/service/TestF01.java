package pizzashop.service;

import org.junit.jupiter.api.*;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TestF01 {
    int table;
    PaymentType paymentType;
    double amount;
    PizzaService pizzaService;
    MenuRepository menuRepository;
    PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        menuRepository=new MenuRepository();
        paymentRepository=new PaymentRepository();
        pizzaService=new PizzaService(menuRepository,paymentRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test Valid ECP")
    void test_valid_ECP() {
        table=2;
        amount=0.01;
        paymentType=PaymentType.Cash;
        try {
            pizzaService.addPayment(table, paymentType, amount);
            assert(true);
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    @Tag("slow")
    void test_non_valid_ECP() {
        table=4;
        amount=-0.01;
        paymentType=PaymentType.Card;
        try {
            pizzaService.addPayment(table, paymentType, amount);
            assert(false);
        } catch (Exception e) {
            assert(true);
            assert(e.getMessage().equals("SUMA PROASTA"));
        }
    }

    @Test
    @Timeout(value=5,unit= TimeUnit.MILLISECONDS)
    void test_non_valid_ECP2() {
        table=11;
        amount=20.011;
        paymentType=PaymentType.Cash;
        try {
            pizzaService.addPayment(table, paymentType, amount);
            assert(false);
        } catch (Exception e) {
            assert(true);
            assert(e.getMessage().equals("MASA PROASTA"));
        }
    }

    @RepeatedTest(2)
    void test_valid_BVA1() {
        table=1;
        amount=48;
        paymentType=PaymentType.Card;
        try {
            pizzaService.addPayment(table, paymentType, amount);
            assert(true);
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    void test_valid_BVA2() {
        table=4;
        amount=2;
        paymentType=PaymentType.Cash;
        try {
            pizzaService.addPayment(table, paymentType, amount);
            assert(true);
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    void test_non_valid_BVA1() {
        table=-3;
        amount=1.1;
        paymentType=PaymentType.Card;
        try {
            pizzaService.addPayment(table, paymentType, amount);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    void test_non_valid_BVA2() {
        table=9;
        amount=-58;
        paymentType=PaymentType.Card;
        try {
            pizzaService.addPayment(table, paymentType, amount);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }



}
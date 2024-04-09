package pizzashop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.*;

class TestF02 {
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
        paymentRepository.deleteAllPayments();
        pizzaService=new PizzaService(menuRepository,paymentRepository);
    }

    @Test
    void F02_TC01(){
        paymentType=PaymentType.Card;
        paymentRepository.add(new Payment(1,paymentType,100));
        assert(pizzaService.getTotalAmount(paymentType)==100);
    }


    @Test
    void F02_TC02(){
        paymentType=PaymentType.Cash;
        paymentRepository.add(new Payment(1,PaymentType.Card,100));
        assert(pizzaService.getTotalAmount(paymentType)==0);
    }


    @Test
    void F02_TC03(){
        paymentType=PaymentType.Card;
        assert(pizzaService.getTotalAmount(paymentType)==0);
    }

    @Test
    void F02_TC04(){
        paymentType=PaymentType.Card;
        paymentRepository.nullAllPayments();
        assert(pizzaService.getTotalAmount(paymentType)==0);
    }

    @Test
    void F02_TC05(){
        paymentType=PaymentType.Card;
        paymentRepository.add(new Payment(1,paymentType,100));
        paymentRepository.add(new Payment(1,PaymentType.Cash,100));
        assert(pizzaService.getTotalAmount(paymentType)==100);
    }


    @AfterEach
    void tearDown() {
    }
}
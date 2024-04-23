package pizzashop.integration.step2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PizzaServiceTest {

    PaymentRepository payRepo;

    PizzaService service;

    @BeforeEach
    void setUp() {
        payRepo=new PaymentRepository();
        service=new PizzaService(null,payRepo);
    }

    @AfterEach
    void tearDown() {
        payRepo.getAll().clear();
        payRepo.writeAll();
    }

    @Test
    void getPayments() {
        List<Payment> result=service.getPayments();
        assertEquals(0,result.size());
    }

    @Test
    void addPayment() throws Exception {
        //Payment payment=new Payment(3,PaymentType.Cash,3);
        Payment payment=mock(Payment.class);
        Mockito.when(payment.getTableNumber()).thenReturn(3);
        Mockito.when(payment.getType()).thenReturn(PaymentType.Cash);
        Mockito.when(payment.getAmount()).thenReturn(3.0);
//        doAnswer((invocation)->{
//            payRepo.add(payment);
//            return null;
//        }).when(payRepo).add(any(Payment.class));


        service.addPayment(payment.getTableNumber(),payment.getType(),payment.getAmount());

        List<Payment> result=service.getPayments();
        assertEquals(1,result.size());
        assertEquals(payment.getTableNumber(),result.get(0).getTableNumber());
        assertEquals(payment.getType(),result.get(0).getType());
        assertEquals(payment.getAmount(),result.get(0).getAmount());
    }
}
package es.upm.grise.profundizacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import es.upm.grise.profundizacion.LoanApprovalService.Applicant;
import es.upm.grise.profundizacion.LoanApprovalService.Decision;

public class LoanApprovalServiceTest {
    
    private final LoanApprovalService service = new LoanApprovalService();

    @Test
    void test1(){
        assertThrows(
            NullPointerException.class,
            () -> service.evaluateLoan(null, 1000, 12)
        );
    }

    @Test
    void test2(){
        Applicant a = new Applicant(2000, 600, false, false);
        assertThrows(
            IllegalArgumentException.class,
            () -> service.evaluateLoan(a, 0, 5)
        );
    }

    @Test
    void test3(){
        Applicant a = new Applicant(2000, 600, false, false);
        assertThrows(
            IllegalArgumentException.class,
            () -> service.evaluateLoan(a, 0, 85)
        );
    }

    @Test
    void test4(){
        Applicant a = new Applicant(2000, 600, false, false);
        assertThrows(
            IllegalArgumentException.class,
            () -> service.evaluateLoan(a, 0, 12)
        );
    }

    @Test
    void test5(){
        Applicant a = new Applicant(-1, 600, false, false);
        assertThrows(
            IllegalArgumentException.class,
            () -> service.evaluateLoan(a, 1000, 12)
        );
    }

    @Test
    void test6(){
        Applicant a = new Applicant(2000, -1, false, false);
        assertThrows(
            IllegalArgumentException.class,
            () -> service.evaluateLoan(a, 1000, 12)
        );
    }

    @Test
    void test7(){
        Applicant a = new Applicant(2000, 851, false, false);
        assertThrows(
            IllegalArgumentException.class,
            () -> service.evaluateLoan(a, 1000, 12)
        );
    }


    @Test
    void test8(){
        Applicant a = new Applicant(2000, 600, false, false);
        assertThrows(
            IllegalArgumentException.class,
            () -> service.evaluateLoan(a, 1000, 3)
        );
    }

    @Test
    void test9(){
        Applicant a = new Applicant(3000, 450, false, false);
        Decision decision = service.evaluateLoan(a, 5000, 24);

        assertEquals(Decision.REJECTED, decision);
    }

    @Test
    void test10(){
        Applicant a = new Applicant(3000, 600, false, false);
        Decision decision = service.evaluateLoan(a, 10000, 24);

        assertEquals(Decision.MANUAL_REVIEW, decision);
    }

    @Test
    void test11(){
        Applicant a = new Applicant(3000, 600, true, false);
        Decision decision = service.evaluateLoan(a, 10000, 24);

        assertEquals(Decision.REJECTED, decision);
    }

    @Test
    void test12(){
        Applicant a = new Applicant(3000, 700, false, false);
        Decision decision = service.evaluateLoan(a, 20000, 36);

        assertEquals(Decision.APPROVED, decision);
    }

    @Test
    void test13(){
        Applicant a = new Applicant(3000, 700, false, false);
        Decision decision = service.evaluateLoan(a, 30000, 36);

        assertEquals(Decision.MANUAL_REVIEW, decision);
    }

    @Test
    void test14(){
        Applicant a = new Applicant(3000, 620, false, true);
        Decision decision = service.evaluateLoan(a, 30000, 36);

        assertEquals(Decision.APPROVED, decision);
    }

    @Test
    void test15(){
        Applicant a = new Applicant(3000, 620, true, true);

        Decision decision = service.evaluateLoan(a, 30000, 36);

        assertEquals(Decision.REJECTED, decision);
    }

    @Test
    void test16() {
        Applicant applicant = new Applicant(3000, 590, false, true);
        Decision decision = service.evaluateLoan(applicant, 15000, 24);

        assertEquals(Decision.MANUAL_REVIEW, decision);
    }
}

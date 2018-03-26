package ru.otus.L6;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static ru.otus.L6.Denomination.*;

/**
 * Created by abyakimenko on 26.03.2018.
 */
public class AtmTest {

    private static final Logger logger = LoggerFactory.getLogger(AtmTest.class);

    private Atm atm;

    @Before
    public void setUp() {
        atm = new Atm();
    }

    @Test
    public void shouldDeposit() {
        atm.deposit(ONE_HUNDRED, 3);
        assertEquals(300, atm.getBalance());
        atm.deposit(FIVE_HUNDREDS, 1);
        atm.deposit(ONE_THOUSAND, 1);
        assertEquals(1800, atm.getBalance());
    }

    @Test
    public void shouldWitdrawWithMinimumNotes() {

        atm.deposit(ONE_THOUSAND, 1);
        atm.deposit(ONE_THOUSAND, 1);
        atm.deposit(ONE_THOUSAND, 1);
        atm.deposit(FIVE_HUNDREDS, 4);
        atm.deposit(FIVE_THOUSANDS, 1);
        atm.deposit(ONE_HUNDRED, 10);
        final int balancBeforeWithdraw = atm.getBalance();
        final int withdrawAmount = 2700;
        Map<Denomination, Integer> withdrawal = atm.withdraw(withdrawAmount);
        assertEquals(2, withdrawal.get(ONE_THOUSAND).intValue());
        assertEquals(1, withdrawal.get(FIVE_HUNDREDS).intValue());
        assertEquals(2, withdrawal.get(ONE_HUNDRED).intValue());

        final int balanceAfterWithdraw = atm.getBalance();
        assertEquals(balancBeforeWithdraw - withdrawAmount, balanceAfterWithdraw);
    }

    @Test(expected = RemainNotEmptyException.class)
    public void shouldThrowExceptionWhenNotEnoughMoney() {
        atm.deposit(ONE_HUNDRED, 5);
        atm.withdraw(1000);
    }

    @Test
    public void shouldNotWitdrawWhenNoApropriateNotes() {

        atm.deposit(ONE_THOUSAND, 5);
        assertEquals(5_000, atm.getBalance());
        try {
            atm.withdraw(1300);
        } catch (Exception e) {
            logger.error("Can't withdraw required sum", e);
        }

        assertEquals(5_000, atm.getBalance());
    }

    @Test
    public void shouldNotWitdrawWhenNoNotes() {

        atm.deposit(ONE_HUNDRED, 5);
        atm.deposit(FIVE_HUNDREDS, 1);
        atm.deposit(ONE_THOUSAND, 4);
        int balanceBefore = atm.getBalance();

        int amount = 2600;
        Map<Denomination, Integer> withdrawal = atm.withdraw(amount);

        assertEquals(balanceBefore - amount, atm.getBalance());

        assertEquals(2, withdrawal.get(ONE_THOUSAND).intValue());
        assertEquals(1, withdrawal.get(FIVE_HUNDREDS).intValue());
        assertEquals(1, withdrawal.get(ONE_HUNDRED).intValue());
    }
}



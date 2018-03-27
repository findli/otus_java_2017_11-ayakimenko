package ru.otus.L7.department;

import org.junit.Before;
import org.junit.Test;
import ru.otus.L7.atm.Atm;
import ru.otus.L7.atm.Denomination;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static ru.otus.L7.atm.Denomination.*;

/**
 * Created by abyakimenko on 27.03.2018.
 */
public class AtmDepartmentTest {

    private AtmDepartment atmDepartment;

    @Before
    public void setUp() {
        atmDepartment = new AtmDepartment();

        Map<Denomination, Integer> initState1 = new EnumMap<>(Denomination.class);
        initState1.put(ONE_HUNDRED, 2);
        initState1.put(ONE_THOUSAND, 3);
        atmDepartment.addAtm("#111", new Atm(initState1));

        Map<Denomination, Integer> initState2 = new EnumMap<>(Denomination.class);
        initState2.put(ONE_HUNDRED, 20);
        initState2.put(ONE_THOUSAND, 5);
        atmDepartment.addAtm("#222", new Atm(initState2));

        Map<Denomination, Integer> initState3 = new EnumMap<>(Denomination.class);
        initState3.put(FIVE_THOUSANDS, 4);
        initState3.put(ONE_THOUSAND, 5);
        atmDepartment.addAtm("#333", new Atm(initState3));
    }

    @Test
    public void addAtm() {

        assertEquals(3, atmDepartment.getAtmSize());
        Map<Denomination, Integer> initState3 = new EnumMap<>(Denomination.class);
        initState3.put(FIVE_THOUSANDS, 1);
        initState3.put(ONE_THOUSAND, 1);
        atmDepartment.addAtm("#444", new Atm(initState3));
        assertEquals(4, atmDepartment.getAtmSize());
    }

    @Test
    public void getTotalBalance() {

        atmDepartment.getAtmByKey("#111").deposit(ONE_HUNDRED, 5);
        atmDepartment.getAtmByKey("#111").deposit(FIVE_HUNDREDS, 5);

        atmDepartment.getAtmByKey("#222").deposit(ONE_THOUSAND, 1);
        atmDepartment.getAtmByKey("#222").deposit(TWO_THOUSANDS, 5);

        atmDepartment.getAtmByKey("#333").deposit(FIVE_THOUSANDS, 10);
        atmDepartment.getAtmByKey("#333").deposit(FIVE_HUNDREDS, 2);

        assertEquals(65_000, atmDepartment.getTotalBalance());
    }

    @Test
    public void restoreAllAtmState() {

        final Atm atm111 = atmDepartment.getAtmByKey("#111");
        atm111.deposit(ONE_HUNDRED, 5);
        atm111.deposit(FIVE_HUNDREDS, 5);
        assertEquals(6_200, atm111.getBalance());

        final Atm atm222 = atmDepartment.getAtmByKey("#222");
        atm222.deposit(ONE_THOUSAND, 1);
        atm222.deposit(TWO_THOUSANDS, 5);
        assertEquals(18_000, atm222.getBalance());

        atmDepartment.restoreAllAtmState();
        assertEquals(3_200, atm111.getBalance());
        assertEquals(7_000, atm222.getBalance());
    }
}
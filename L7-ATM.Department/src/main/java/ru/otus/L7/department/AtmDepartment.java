package ru.otus.L7.department;

import ru.otus.L7.atm.Atm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abyakimenko on 17.01.2018.
 * <p>
 * Приложение может содержать несколько ATM
 * Department может собирать сумму остатков со всех ATM
 * Department может инициировать событие – восстановить состояние всех ATM до начального.
 * (начальные состояния у разных ATM могут быть разными)
 */
public class AtmDepartment {

    private Map<String, Atm> atmMap = new HashMap<>();

    public void addAtm(String atmKey, Atm atm) {
        atmMap.putIfAbsent(atmKey, atm);
    }

    public Atm getAtmByKey(String atmKey) {
        return atmMap.get(atmKey);
    }

    public int getAtmSize() {
        return atmMap.size();
    }

    public int getTotalBalance() {
        return atmMap.entrySet().stream().mapToInt(entry -> entry.getValue().getBalance()).sum();
    }

    public void restoreAllAtmState() {
        atmMap.values().forEach(Atm::restoreState);
    }
}

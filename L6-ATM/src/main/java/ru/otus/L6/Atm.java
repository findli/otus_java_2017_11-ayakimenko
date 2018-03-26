package ru.otus.L6;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by abyakimenko on 26.03.2018.
 */
public class Atm {

    private Map<Denomination, Integer> moneyStorage = new EnumMap<>(Denomination.class);

    public void deposit(Denomination denomination, int count) {
        int notesCount = moneyStorage.getOrDefault(denomination, 0) + count;
        moneyStorage.put(denomination, notesCount);
    }

    public Map<Denomination, Integer> withdraw(int amount) {
        Map<Denomination, Integer> withdrawal = new EnumMap<>(Denomination.class);
        int remainder = amount;
        for (Denomination denomination : Denomination.values()) {
            int notesCount = moneyStorage.getOrDefault(denomination, 0);
            if (notesCount > 0) {
                // check minimum count
                int neededNotes = remainder / denomination.getValue();
                if (neededNotes > 0) {
                    int notesToGet = Math.min(notesCount, neededNotes);
                    withdrawal.put(denomination, notesToGet);
                    remainder -= denomination.getValue() * notesToGet;
                }
            }
        }

        checkRemainEmpty(amount, remainder);

        // update moneyStorage
        withdrawal.forEach(((denomination, count) -> moneyStorage.compute(denomination, (k, v) -> v - count)));

        return withdrawal;
    }

    public int getBalance() {
        return moneyStorage
                .entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }

    private void checkRemainEmpty(int amount, int remainder) {
        if (remainder > 0) {
            throw new RemainNotEmptyException(String.format("Can't withdraw amount %d from moneyStorage %s",
                    amount,
                    moneyStorage));
        }
    }
}

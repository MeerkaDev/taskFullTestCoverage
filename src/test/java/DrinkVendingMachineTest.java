import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DrinkVendingMachineTest{

    private static DrinkVendingMachine drinkVendingMachine;
    private static Random random = random = new Random();;

    @BeforeEach
    public void beforeEach() {
        drinkVendingMachine = new DrinkVendingMachine();
    }

    @Test
    public void shouldReturnBalance() {
        assertEquals(0, drinkVendingMachine.getBalance());
    }

    @Test
    public void shouldReturnZeroQuantityWhenNoSuchDrinkInMachine() {
        assertEquals(0, drinkVendingMachine.getDrinkQuantity("Плохой Напиток"));
    }

    @Test
    public void shouldReturnQuantityWhenDrinkIsInMachine() {
        assertEquals(5, drinkVendingMachine.getDrinkQuantity("Фанта"));
    }

    @Test
    public void balanceShouldGrowWhenValueIsPositive() {
        int expectedBalance = random.nextInt(Integer.MAX_VALUE);

        drinkVendingMachine.insertCoin(expectedBalance);

        assertEquals(expectedBalance, drinkVendingMachine.getBalance() );
    }

    @Test
    public void balanceShouldNotGrowWhenValueIsNotPositive() {
        String expectedMessage = "Число должно быть положительным";

        Executable executable = () -> drinkVendingMachine.insertCoin(random.nextInt(1));;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void shouldGiveMessageAndReduceDrinkQuantityAndBalanceWhenEnoughDrinkAndCorrectDrinkNameAndBalanceEnough() {
        String drinkName = "Фанта";
        int expectedQuantity = 4;
        int expectedBalance = 50;
        String expectedMessage = "Вы получили " + drinkName + "!";

        drinkVendingMachine.insertCoin(250);
        String gotMessage = drinkVendingMachine.selectDrink(drinkName);

        assertEquals(expectedQuantity, drinkVendingMachine.getDrinkQuantity(drinkName));
        assertEquals(expectedBalance, drinkVendingMachine.getBalance());
        assertEquals(expectedMessage, gotMessage);
    }

    @Test
    public void shouldReturnMessageWhenBalanceNotEnoughAndDrinkIsInMachine() {
        String drinkName = "Фанта";
        String expectedMessage = "Недостаточно средств.";

        drinkVendingMachine.insertCoin(199);
        String gotMessage = drinkVendingMachine.selectDrink(drinkName);

        assertEquals(expectedMessage, gotMessage);
    }

    @Test
    public void shouldReturnMessageWhenNoSuchDrinkInMachine() {
        String expectedMessage = "Такого напитка нет в автомате.";

        assertEquals(expectedMessage, drinkVendingMachine.selectDrink("Плохой Напиток"));
    }

    @Test
    public void shouldReturnMessageWhenDrinkIsOutOfStock() {
        String drinkName = "Фанта";
        String expectedMessage = "Извините, " + drinkName + " закончился.";

        drinkVendingMachine.insertCoin(1500);
        while (drinkVendingMachine.getDrinkQuantity(drinkName) != 0) {
            drinkVendingMachine.selectDrink(drinkName);
        }

        assertEquals(expectedMessage, drinkVendingMachine.selectDrink(drinkName));
    }
}
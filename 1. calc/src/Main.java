import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * Коновченко Александр
 * Домашнее задание по итогам лекции 12
 * Оптировать калькулятор из урока.
 * 1. Изменить внешний вид, приблизить внешний вид к Windows калькулятору.
 * 2. Добавьте пару новых операций, например корень квадратный и корень числа.
 * 3. Должна быть возможность выполнять действия с числами, в которых больше одной цифры.
 * 4. Добавить меню с возможностью отображать описание калькулятора.
 */

public class Main {
    public static void main(String[] args) throws IOException, FontFormatException, ParseException {
        new Calculator();
    }
}

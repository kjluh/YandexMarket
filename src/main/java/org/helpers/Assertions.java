package org.helpers;

import io.qameta.allure.Step;

public class Assertions {
    /**
     * Переопределение метода проверки, для отображения его в степах
     * @author Анатолий Плахов
     * @param condition результат проверки
     * @param message сообщение для при неудачной проверке
     */
    @Step("Проверяем что нет ошибки: {message}")
    public static void assertTrue(boolean condition, String message) {
        org.junit.jupiter.api.Assertions.assertTrue(condition, message);
    }

}

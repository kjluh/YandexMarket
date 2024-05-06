package org.helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

/**
 * Класс в котором отражены проверяемые критерии
 * @author Анатолий Плахов
 */
public class DataProvider {
    /**
     * Данные для выбора категории товаров
     * @author Анатолий Плахов
     */
    private static final String categoryProduct = "Ноутбуки и компьютеры";
    /**
     * Данные для выбора группы товаров в категории
     * @author Анатолий Плахов
     */
    private static final String nameProduct = "Ноутбуки";
    /**
     * Минимальная цена для поиска
     * @author Анатолий Плахов
     */
    private static final Integer minPrice = 10000;
    /**
     * Максимальная цена для поиска
     * @author Анатолий Плахов
     */
    private static final Integer maxPrice = 30000;
    /**
     * Лист производителей для поиска
     * @author Анатолий Плахов
     */
    private static final List<String> creators = List.of("Lenovo", "HP");
    /**
     * Минимальное количество найденных товаров в результате поиска товаров
     * @author Анатолий Плахов
     */
    private static final Integer searchCount = 12;
    /**
     * Номер строки товара на странице результатов поиска для вставки в строку поиска и сравнения результатов
     * @author Анатолий Плахов
     */
    private static final Integer numberStringForSearch = 0;

    /**
     * Возвращает стрим аргументов для работы с ним в тестах
     * @author Анатолий Плахов
     * @return стрим аргументов
     */
    public static Stream<Arguments> startCriteriaSearch() {
        return Stream.of(
                Arguments.of(categoryProduct, nameProduct, minPrice, maxPrice, creators ,searchCount, numberStringForSearch));
    }
}

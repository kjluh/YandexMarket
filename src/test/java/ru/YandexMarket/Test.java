package ru.YandexMarket;

import org.helpers.Assertions;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.page.YMFilter;
import org.page.YMPageResult;
import org.page.YMStart;

import java.util.List;

import static org.helpers.Properties.testsProperties;

/**
 * Класс для проверки результатов поиска на яндекс маркете
 * @author Анатолий Плахов
 */
public class Test extends BaseTest {

    @Feature("Проверка яндекс маркета")
    @DisplayName("Проверка яндекс маркета - ноутбуки")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("org.helpers.DataProvider#startCriteriaSearch")
    public void testYM(String categoryProduct, String nameProduct, Integer minPrice, Integer maxPrice,
                       List<String> creators, Integer searchCount, Integer numberStringForSearch) {
        chromeDriver.get(testsProperties.YMarketUrl());
        YMStart ymStart = new YMStart(chromeDriver);
        ymStart.selectCatalogAndProducts(categoryProduct, nameProduct);
        YMFilter ymFilter = new YMFilter(chromeDriver);
        ymFilter.clickPrice(minPrice, maxPrice);
        ymFilter.clickCreator(creators);
        YMPageResult ymPageResult = new YMPageResult(chromeDriver, creators);
        ymPageResult.countResultSearch(searchCount);
        ymPageResult.checkSearchFilterInPage();
        ymPageResult.checkSearchFilterEveryPage();
        ymPageResult.takeFirstTargetFromFirstPage(numberStringForSearch);
        ymPageResult.inputNameFirstTargetInSearch();
    }
}

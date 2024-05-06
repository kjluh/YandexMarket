package org.page;

import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.helpers.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для различных проверок результатов поиска на соответствие заданным параметрам поиска
 *
 * @author Анатолий Плахов
 */
public class YMPageResult extends Chromedriver {
    private final List<String> stringsToCheck;
    private String nameTarget;


    public YMPageResult(WebDriver chromeDriver, List<String> creators) {
        super(chromeDriver);
        stringsToCheck = creators;
    }

    /**
     * Проверяет количество отображаемых результатов
     *
     * @author Анатолий Плахов
     */
    @Step("Возвращаем и сверяем количество найденных результатов")
    public void countResultSearch(Integer searchCount) {
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-zone-name='searchPage']")));
        Assertions.assertTrue(chromeDriver.findElements(By.xpath
                ("//article[@data-auto='searchOrganic']//div[@data-baobab-name='title']")).size() > searchCount, "найдено менее 12 единиц");

    }

    /**
     * Проверяет каждое найденное предложение на странице
     *
     * @author Анатолий Плахов
     */
    @Step("проверяем первую страницу результатов")
    public void checkSearchFilterInPage() {
        Assertions.assertTrue(resultSearchFilterInPage(), "не соответствует производителю");
    }

    /**
     * Проверяет каждое найденное предложение на странице, затем переходим к следующей странице результатов
     *
     * @author Анатолий Плахов
     */
    @Step("Проверяем каждую страницу результатов")
    public void checkSearchFilterEveryPage() {
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath
                ("//div[@data-cms-page-id='search-page']")));
        List<WebElement> webElementListSwitch = chromeDriver.findElements(By.xpath
                ("//button[@data-auto='pager-more']"));
        while (webElementListSwitch.size() > 0) {
            Actions actions = new Actions(chromeDriver);
            actions.moveToElement(webElementListSwitch.stream().findFirst().get()).perform();
            webElementListSwitch.stream().findFirst().get().click();
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-baobab-name='more']//button[@disabled]")));
            webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@data-baobab-name='more']//button[@disabled]")));
            webElementListSwitch = chromeDriver.findElements(By.xpath("//button[@data-auto='pager-more']"));
        }
        boolean resultSearch = resultSearchFilterInPage();
        Assertions.assertTrue(resultSearch, "не соответствует производителю на какой-то странице");
    }

    /**
     * Получает заданную позицию с первой страницы
     *
     * @author Анатолий Плахов
     */
    @Step("Получаем название товара с первой страницы и {number} строки")
    public void takeFirstTargetFromFirstPage(Integer number) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-zone-name='searchResults']")));
        List<WebElement> elements = chromeDriver.findElements(By.xpath(
                "//div[@data-zone-name='searchResults']//div[@data-auto-themename='listDetailed']/div//h3"));
        if (number > elements.size()) {
            Assertions.assertTrue(false, "такого номера элемента нет на странице");
        } else {
            nameTarget = elements.get(number).getText();
        }
    }

    /**
     * Вводит в поиск товар и сверяет его с первой страницей поиска
     *
     * @author Анатолий Плахов
     */
    @Step("Ищем результат с первой страницы и первой строки в поиске, затем сверяем с результатами поиска")
    public void inputNameFirstTargetInSearch() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-zone-name='search-input']")));
        chromeDriver.findElement(By.xpath("//div[@data-zone-name='search-input']/input[@type='text']")).sendKeys(nameTarget);
        chromeDriver.findElement(By.xpath("//button[@data-auto='search-button']")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath
                ("//div[@data-zone-name='searchResults']")));
        List<String> list = chromeDriver.findElements(By.xpath
                        ("//div[@data-zone-name='searchResults']//div[@data-auto-themename='listDetailed']/div//h3"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
        Assertions.assertTrue(list.contains(nameTarget), "в результатах поиска, на первой странице, нет искомого товар");

    }


    /**
     * Проверка массива слов на наличие поисковых слов внутри
     *
     * @param arrayStringForCheck       Массив слов в которых ищем совпадение
     * @param arrayStringWhichNeedCheck Массив слов которые ищем
     * @return результат проверки
     * @author Анатолий Плахов
     */
    private boolean checkString(List<String> arrayStringForCheck, List<String> arrayStringWhichNeedCheck) {
        for (String s : arrayStringForCheck) {
            for (String word : arrayStringWhichNeedCheck) {
                if (StringUtils.containsIgnoreCase(s, word))
                    return true;
            }
        }
        return false;
    }

    /**
     * Проверяет предложения на странице, если объект не соответствует, открывается в новой вкладке
     * карточка объекта и более детально сравниваются результаты
     *
     * @return boolean результат
     * @author Анатолий Плахов
     */
    private boolean resultSearchFilterInPage() {
        Map<String, String> newWebElementList = chromeDriver.findElements(By.xpath
                        ("//article[@data-auto='searchOrganic']//div[@data-baobab-name='title']//a"))
                .stream().filter(e -> !checkString(Arrays.asList(e.getText()
                        .split(" ")), stringsToCheck))
                .collect(Collectors.toMap(WebElement::getText, e -> e.getAttribute("href")));

        boolean replaceCheck = true;
        if (newWebElementList.size() > 0) {
            for (Map.Entry<String, String> element : newWebElementList.entrySet()) {
                WebDriver newD = new ChromeDriver();
                WebDriverWait webDriverWait = new WebDriverWait(newD, 30);
                newD.get(element.getValue());
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-baobab-name='spec']")));
                WebElement we = newD.findElement(By.xpath("//div[@data-baobab-name='spec']"));
                replaceCheck = checkString(Arrays.asList(we.getText().split(" ")), stringsToCheck);
                if (!replaceCheck) {
                    System.out.println(element.getKey() + " этот элемент не соответствует поиску");
                }
                newD.quit();
            }
            return replaceCheck;
        } else {
            return true;
        }
    }
}

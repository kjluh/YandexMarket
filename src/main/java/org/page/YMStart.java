package org.page;

import io.qameta.allure.Step;
import org.helpers.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Начальная страница яндекс Маркета, в которой выбирается категория и группа товаров
 *
 * @author Анатолий Плахов
 */
public class YMStart extends Chromedriver {
    private final WebElement elementButton;

    public YMStart(WebDriver chromeDriver) {
        super(chromeDriver);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-baobab-name='catalog']//button")));
        this.elementButton = chromeDriver.findElement(By.xpath("//div[@data-baobab-name='catalog']//button"));
    }

    /**
     * Выбирает каталог и группу товаров
     * и проверяет выбор группы товаров
     *
     * @author Анатолий Плахов
     */
    @Step("Переходим в категорию продуктов {categoryProduct} группу {nameProduct}")
    public void selectCatalogAndProducts(String categoryProduct, String nameProduct) {
        elementButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@data-zone-name='catalog-content']//ul[@role='tablist']")));
        Actions actions = new Actions(chromeDriver);
        actions.moveToElement(chromeDriver.findElement(By.xpath
                        ("//div[@data-zone-name='catalog-content']//ul[@role='tablist']//a[contains(.,'" + categoryProduct + "')]")))
                .perform();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                ("//div[@data-zone-name='catalog-content']//ul[@role='tablist']//li[@aria-selected='true']" +
                                "//a[contains(.,'" + categoryProduct + "')]")));
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@role='heading' and @aria-level='2']")));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[@role='heading']//ul//div[contains(.,'" + nameProduct + "') and @data-zone-name='link']")));
        chromeDriver.findElement(By.xpath("//div[@role='heading']//ul//div[contains(.,'" + nameProduct + "') and @data-zone-name='link']")).click();
        Assertions.assertTrue(chromeDriver.findElement(By.xpath("//div[@id='/content/page/fancyPage/cms/1']//h1"))
                .getText().contains(nameProduct), "Ноуты не найдены");
    }
}
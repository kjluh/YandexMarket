package org.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Начальный класс для инициализации
 * @author Анатолий Плахов
 */
public class Chromedriver {
    /**
     * Хромдрайвер
     * @author Анатолий Плахов
     */
    public WebDriver chromeDriver;
    /**
     * Ожидание
     * @author Анатолий Плахов
     */
    public WebDriverWait webDriverWait;


    public Chromedriver(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        this.webDriverWait = new WebDriverWait(chromeDriver, 20);
    }

    /**
     * Метод для ожидания загрузки результатов на странице
     * @author Анатолий Плахов
     */
    public void load() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-auto='SerpStatic-loader']")));
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@data-auto='SerpStatic-loader']")));
    }

}

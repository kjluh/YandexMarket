package org.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

/**
 * В каталоге задает условия для поиска необходимого товара
 *
 * @author Анатолий Плахов
 */
public class YMFilter extends Chromedriver {


    public YMFilter(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    /**
     * Выставляет минимальный и максимальный диапазон цен
     *
     * @author Анатолий Плахов
     */
    @Step("Выставляем ценовой фильтр")
    public void clickPrice(Integer minPrice, Integer maxPrice) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                ("//div[@data-filter-id='glprice']")));
        WebElement minPriceField = chromeDriver.findElement(By.xpath
                ("//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-min']//input"));
        minPriceField.click();
        minPriceField.sendKeys(String.valueOf(minPrice));
        WebElement maxPriceField = chromeDriver.findElement(By.xpath
                ("//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-max']//input"));
        maxPriceField.click();
        maxPriceField.sendKeys(String.valueOf(maxPrice));
        load();
    }

    /**
     * Выставляет нужных производителей
     *
     * @author Анатолий Плахов
     */
    @Step("Выставляем фильтр по производителю")
    public void clickCreator(List<String> creators) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[@data-grabber='SearchFilters']/div[contains(.,'Производитель')]//button")));
        chromeDriver.findElement(By.xpath("//div[@data-grabber='SearchFilters']/div[contains(.,'Производитель')]//button")).click();
        for (String creator : creators) {
            chromeDriver.findElements(By.xpath
                            ("//div[@data-grabber='SearchFilters']/div[contains(.,'Производитель')]//div[@data-zone-name='FilterValue']//label"))
                    .stream().filter(e -> e.getText().contains(creator)).filter(e -> e.getAttribute("aria-checked").equals("false"))
                    .forEach(e->{e.click();
                        load();
                    });
        }
    }
}
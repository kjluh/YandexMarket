package ru.YandexMarket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.helpers.Properties.testsProperties;

public class BaseTest {
    protected WebDriver chromeDriver;

    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", System.getenv(testsProperties.chrome()));
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        chromeDriver.manage().window().maximize();
    }

    @AfterEach
    public void after() {
        chromeDriver.quit();
    }
}

package org.helpers;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/test/resources/tests.properties"
})
public interface TestsProperties extends Config {
    /**
     * Стартовый url
     * @return url
     * @author Анатолий Плахов
     */
    @Key("yandexMarket.url")
    String YMarketUrl();

    /**
     * Тип браузера для использования
     * @return браузер
     * @author Анатолий Плахов
     */
    @Key("browser")
    String chrome();
}

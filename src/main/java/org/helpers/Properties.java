package org.helpers;

import org.aeonbits.owner.ConfigFactory;

public class Properties {
    /**
     * Статический метод для работы с пропертями
     * @author Анатолий Плахов
     */
    public static TestsProperties testsProperties = ConfigFactory.create(TestsProperties.class);
}

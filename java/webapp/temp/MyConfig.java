package com.linewx.law.instrument.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by luganlin on 12/18/16.
 */
public interface MyConfig {
    @Value("dataSource.driverClassName")
    String getDriver();
}

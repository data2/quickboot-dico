package com.data2.salmon.core.engine.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static com.data2.salmon.core.engine.enums.DataBase.ORACLE;

/**
 * @author leewow
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "spring.salmon.database.oracle")
@Configuration
public class OracleConfig {
    private String url;
    private String username;
    private String password;

    @Bean(name = "oracle")
    public DruidDataSource builder() {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            log.info("oracle config not right, url or username or passwd is null!");
            return null;
        }
        if (!url.contains(ORACLE.getCode())) {
            log.info("oracle url config not contain jdbc str!");
            return null;
        }
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        return dataSource;
    }
}

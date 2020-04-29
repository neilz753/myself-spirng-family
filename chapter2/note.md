#### 好用的连接池-HikariCP

Hikari 日语 光

官网 https://github.com/brettwooldridge/HikariCP

**Spring Boot 2.x**

- 默认使用 HikariCP
- 配置 spring.datasource.hikari.* 配置

**Spring Boot 1.x**

- 默认使用 Tomcat 连接池，需要移除 tomcat-jdbc 依赖
- spring.datasource.type=com.zaxxer.hikari.HikariDataSource

spring-boot-autoconfigure-2.2.6.RELEASE.jar  包里 jdbc/DataSourceConfiguration.java 中就有相关代码。

```java
    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnClass({HikariDataSource.class})
    @ConditionalOnMissingBean({DataSource.class})
    @ConditionalOnProperty(
        name = {"spring.datasource.type"},
        havingValue = "com.zaxxer.hikari.HikariDataSource",
        matchIfMissing = true
    )
    static class Hikari {
        Hikari() {
        }

        @Bean
        @ConfigurationProperties(
            prefix = "spring.datasource.hikari"
        )
        HikariDataSource dataSource(DataSourceProperties properties) {
            HikariDataSource dataSource =
            (HikariDataSource)DataSourceConfiguration.
            createDataSource(properties,HikariDataSource.class);
            if (StringUtils.hasText(properties.getName())) {
                dataSource.setPoolName(properties.getName());
            }

            return dataSource;
        }
    }
```


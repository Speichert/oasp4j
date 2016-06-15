package io.oasp.gastronomy.restaurant;

import org.owasp.appsensor.core.AppSensorServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import io.oasp.gastronomy.restaurant.tablemanagement.service.impl.rest.TablemanagementRestServiceImpl;
import io.oasp.module.jpa.dataaccess.api.AdvancedRevisionEntity;

//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class })
@SpringBootApplication(exclude = { EndpointAutoConfiguration.class })
@EntityScan(basePackages = { "io.oasp.gastronomy.restaurant" }, basePackageClasses = { AdvancedRevisionEntity.class })
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Configuration
@EnableAutoConfiguration
@ComponentScan(value = "org.owasp.appsensor", excludeFilters = @ComponentScan.Filter(value = AppSensorServer.class, type = FilterType.ASSIGNABLE_TYPE) )
public class SpringBootApp {

  /**
   * Entry point for spring-boot based app
   *
   * @param args - arguments
   */
  public static void main(String[] args) {

    ConfigurableApplicationContext context = SpringApplication.run(SpringBootApp.class, args);
    TablemanagementRestServiceImpl simpleGenerator = context.getBean(TablemanagementRestServiceImpl.class);
    System.out.println(simpleGenerator.toString());

  }
}

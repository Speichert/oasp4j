package io.oasp.gastronomy.restaurant.general.configuration;

import javax.inject.Inject;

import org.owasp.appsensor.core.AppSensorClient;
import org.owasp.appsensor.core.event.EventManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * This auto configuration will be used by spring boot to enable traditional deployment to a servlet container. You may
 * remove this class if you run your application with embedded tomcat only. Tomcat startup will be twice as fast.
 */
@Configuration
@ComponentScan(basePackages = "org.owasp.appsensor")
@ImportResource({ "appsensor-client-config.xml", "appsensor-server-config.xml" })
public class AppSensorConfig {

  @Inject
  AppSensorClient appsensorClient;

  @Bean
  protected EventManager getEventManager() {

    return this.appsensorClient.getEventManager();
  }

}

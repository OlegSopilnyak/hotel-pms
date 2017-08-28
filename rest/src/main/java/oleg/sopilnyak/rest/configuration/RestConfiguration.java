package oleg.sopilnyak.rest.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration for module REST
 */
@Slf4j
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"oleg.sopilnyak.rest.controller"})
public class RestConfiguration extends WebMvcConfigurerAdapter {

}

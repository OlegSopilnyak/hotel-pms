package oleg.sopilnyak.service.runner;

import lombok.extern.slf4j.Slf4j;
import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * Runner of REST controllers
 */

@Slf4j
@SpringBootApplication
@Import(ApplicationConfiguration.class)
public class Application {
    public static void main(String[] parameters) {
        log.info("Begin launching");
        SpringApplication application = new SpringApplication(Application.class);
        // setup Spring profile
        application.setAdditionalProfiles("development");
        application.run(parameters);

    }

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        registration.addInitParameter("webAllowOthers", "true");
        return registration;
    }

}

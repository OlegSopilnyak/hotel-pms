package oleg.sopilnyak.service.runner;

import oleg.sopilnyak.common.model.configuration.ModelConfiguration;
import oleg.sopilnyak.common.service.configuration.ServiceConfiguration;
import oleg.sopilnyak.persistence.configuration.PersistenceConfiguration;
import oleg.sopilnyak.rest.configuration.RestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration for runner
 */
@Configuration
@Import({ModelConfiguration.class, ServiceConfiguration.class, RestConfiguration.class, PersistenceConfiguration.class})
public class ApplicationConfiguration {
}

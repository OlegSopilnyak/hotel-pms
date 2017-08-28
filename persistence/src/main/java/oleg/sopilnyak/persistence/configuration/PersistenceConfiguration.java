package oleg.sopilnyak.persistence.configuration;

import oleg.sopilnyak.persistence.service.PersistenceServiceImpl;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

/**
 * Persistence Model configuration for persistence beans
 */
@Configuration
@Import({JpaHibernateConfiguration.class})
public class PersistenceConfiguration {

    @Bean(autowire = Autowire.BY_TYPE)
    public PersistenceServiceImpl buildPersistenceService(){
        return new PersistenceServiceImpl();
    }

    @Bean(name = "persistenceMapper")
    public DozerBeanMapper buildDozerBeanMapper(){
        List<String> mappingFiles = Arrays.asList(
                "dozerJdk8Converters.xml",
                "dozer_mapping.xml"
        );

        return  new DozerBeanMapper(mappingFiles);
    }
}

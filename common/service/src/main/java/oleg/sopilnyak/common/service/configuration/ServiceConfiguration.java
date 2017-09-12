package oleg.sopilnyak.common.service.configuration;

import lombok.extern.slf4j.Slf4j;
import oleg.sopilnyak.common.facade.impl.CustomerFacadeImpl;
import oleg.sopilnyak.common.service.impl.IdGeneratorServiceImpl;
import oleg.sopilnyak.common.service.impl.ReservationServiceImpl;
import oleg.sopilnyak.common.service.impl.TimeServiceImpl;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Configuration for core services
 */
@Slf4j
@Configuration
public class ServiceConfiguration {

    /**
     * Definition of customer facade
     * @return singleton
     */
    @Bean(name = "customerFacade", autowire = Autowire.BY_TYPE)
    public CustomerFacadeImpl buildCustomerFacade(){
        log.debug("Building customer facade.");
        return new CustomerFacadeImpl();
    }

    /**
     * Definition of reservation service
     *
     * @return singleton
     */
    @Bean(name = "reservationService", autowire = Autowire.BY_TYPE, initMethod = "init", destroyMethod = "destroy")
    public ReservationServiceImpl buildReservationService(){
        log.debug("Building reservation service.");
        return new ReservationServiceImpl();
    }
    /**
     * Generator of unique Ids
     *
     * @return singleton
     */
    @Bean
    public IdGeneratorServiceImpl buildIdGeneratorService(){
        log.debug("Building unique id generator.");
        return new IdGeneratorServiceImpl();
    }

    /**
     * Service of  date-time
     *
     * @return singleton
     */
    @Bean
    public TimeServiceImpl buildTimeService(){
        log.debug("Building date-time service.");
        return new TimeServiceImpl();
    }

    @Bean(name = "coreServiceMapper")
    public DozerBeanMapper buildDozerBeanMapper(){
        List<String> mappingFiles = Collections.singletonList(
                "dozerJdk8Converters.xml"
        );

        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }

    @Bean(name = "coreServiceRunner")
    public ScheduledExecutorService coreRunner(){
        return new ScheduledThreadPoolExecutor(2);
    }
}

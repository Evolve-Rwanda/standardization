package com.example.springauth;

import com.example.springauth.logging.loggers.Logger;
import com.example.springauth.services.LogService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@EnableEncryptableProperties
public class SpringAuthApplication {


    public static void main(String[] args) {

        StandardizationManager standardizationManager = new StandardizationManager();
        standardizationManager.initializeStandardAuthModule();
        ApplicationContext context = SpringApplication.run(SpringAuthApplication.class, args);

        LogService logService = context.getBean(LogService.class);
        Logger logger = Logger.getLogger();
        logger.setLogService(logService);

        logger.info(Logger.INFO_INIT);
        logger.error(Logger.ERROR_INIT);
        logger.warning(Logger.WARNING_INIT);
        logger.debug(Logger.DEBUG_INIT);
        logger.verbose(Logger.VERBOSE_INIT);
        logger.info("This is a simple log.");
    }
}

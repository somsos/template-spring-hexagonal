package daj.adapter.common;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class EndpointsListener {

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        final var endpoints = new ArrayList<String>();
        endpoints.add("\n");
        applicationContext.getBean(RequestMappingHandlerMapping.class)
            .getHandlerMethods().forEach((key, value) -> {
                endpoints.add(key.toString() + "\n");
            }
        );
        log.info("Endpoints: " + endpoints);
    }
}
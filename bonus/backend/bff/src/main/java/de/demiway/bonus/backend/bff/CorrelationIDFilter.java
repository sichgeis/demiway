package de.demiway.bonus.backend.bff;

import org.slf4j.MDC;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.util.UUID;

class CorrelationIDFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        MDC.put("correlation-id", UUID.randomUUID().toString());
    }
}

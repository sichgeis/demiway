package de.demiway.bonus.backend.bff;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseHeaderFilter implements ContainerResponseFilter {

    private final AppConfiguration appConfiguration;

    public ResponseHeaderFilter(final AppConfiguration configuration) {
        this.appConfiguration = configuration;
    }

    @Override
    public void filter(final ContainerRequestContext request,
                       final ContainerResponseContext response) {

        final var headers = response.getHeaders();

        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS, DELETE");
        headers.add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Content-Type, Link, Set-Cookie");
        headers.add("Access-Control-Expose-Headers", "Content-Type, Link, Set-Cookie");
        headers.add("X-Content-Type-Options", "nosniff");
        headers.add("Access-Control-Allow-Credentials", "true");

        rewriteContentTypeHeader(headers, response.getMediaType());

        if (appConfiguration.getEnvironment().isLocal) {
            allowCrossOrigin(headers, request.getHeaderString("Origin"));
        }

        if (appConfiguration.getEnvironment().allowLocalhostOrigin) {
            var defaultAllowedUri = "http://localhost:3000";
            allowCrossOrigin(headers, defaultAllowedUri);
        }
    }

    private static void rewriteContentTypeHeader(final MultivaluedMap<String, Object> headers, final MediaType type) {
        // ASVS 4.0.1 item 14.4.1 ASVS 3.0 item V11.2
        if (type != null && !type.toString().contains("charset")) {
            headers.putSingle("Content-Type", type.withCharset("utf-8").toString());
        }
    }

    private static void allowCrossOrigin(final MultivaluedMap<String, Object> headers, final String origin) {
        headers.add("Access-Control-Allow-Origin", origin);
    }
}

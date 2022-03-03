package de.demiway.bonus.backend.bff.resources;

import de.demiway.bonus.backend.bff.auth.Identity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final String cognitoClientId;
    private final String cognitoDomain;

    public UserResource(String cognitoClientId, String cognitoDomain) {
        this.cognitoClientId = cognitoClientId;
        this.cognitoDomain = cognitoDomain;
    }

    @GET
    @Path("/identity")
    public Identity identity(@Context HttpHeaders headers) {
        var jwtHeader = headers.getRequestHeader("X-Amzn-Oidc-Data");

        if (jwtHeader != null && !jwtHeader.isEmpty()) {
            return Identity.fromJWT(jwtHeader.get(0));
        }

        return Identity.unidentified();
    }

    @GET
    @Path("/logout")
    public Response logout(@Context UriInfo uriInfo, @Context HttpHeaders headers) {

        var domain = uriInfo.getBaseUri().getHost();

        return Response.ok(logoutResponse(cognitoClientId, cognitoDomain, domain))
                // Cognito issues three cookie chunks at most
                // https://docs.aws.amazon.com/elasticloadbalancing/latest/application/listener-authenticate-users.html#authentication-flow
                .header("Set-Cookie", "AWSELBAuthSessionCookie-0=deleted;path=/;expires=Thu, 01 Jan 1970 00:00:00 GMT;")
                .header("Set-Cookie", "AWSELBAuthSessionCookie-1=deleted;path=/;expires=Thu, 01 Jan 1970 00:00:00 GMT;")
                .header("Set-Cookie", "AWSELBAuthSessionCookie-2=deleted;path=/;expires=Thu, 01 Jan 1970 00:00:00 GMT;")
                .build();
    }

    private static String logoutResponse(String cognitoClientId, String cognitoDomain, String hostname) {
        return logoutJson("https://" + cognitoDomain + "/logout"
                + "?client_id=" + cognitoClientId
                + "&response_type=code&redirect_uri=https://" + hostname + "/"
        );
    }

    private static String logoutJson(String url) {
        return "{ \"logout\": \"" + url + "\" }";
    }

}

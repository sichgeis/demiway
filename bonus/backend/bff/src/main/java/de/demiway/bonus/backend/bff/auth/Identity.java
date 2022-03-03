package de.demiway.bonus.backend.bff.auth;

import com.auth0.jwt.JWT;

import java.util.Objects;

public final class Identity {

    public String email;

    @SuppressWarnings("unused")
    private Identity() {
    }

    private Identity(String email) {
        this.email = email;
    }

    public static Identity unidentified() {
        return new Identity("<unidentified>");
    }

    public static Identity fromJWT(String token) {
        var jwt = JWT.decode(token);

        var email = jwt.getClaim("email").asString();

        return new Identity(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var identity = (Identity) o;
        return Objects.equals(email, identity.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

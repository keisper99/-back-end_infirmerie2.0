


package org.institutsaintjean.INFIRMERIE_IUSJC.Configurations.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.InfirmiereDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Infirmiere_Metier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final Infirmiere_Metier infirmiere_metier;

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(InfirmiereDto user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 86400000); // 24 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(user.getLogin())
                .withSubject(String.valueOf(user.getIdInfirmiere()))
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("nom", user.getNom())
                .withClaim("prenom", user.getPrenom())
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        InfirmiereDto user = InfirmiereDto.builder()
                .idInfirmiere(Long.parseLong(decoded.getSubject()))
                .login(decoded.getIssuer())
                .nom(decoded.getClaim("nom").asString())
                .prenom(decoded.getClaim("prenom").asString())
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        InfirmiereDto user = infirmiere_metier.findByLogin(decoded.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

}



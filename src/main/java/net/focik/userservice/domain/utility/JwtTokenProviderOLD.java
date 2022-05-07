package net.focik.userservice.domain.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static net.focik.userservice.domain.security.constant.SecurityConstant.*;

@Component
public class JwtTokenProviderOLD {

//    @Value("${jwt.secret: defywuvUKYOmqa6lMC7BgUlADXlKuq4CPYc}")
    private String secret = "defywuvUKYOmqa6lMC7BgUlADXlKuq4CPYc";

//    @Value("${jwt.secret2}")
//    private  String secret2;
    private  Algorithm algorithm = Algorithm.HMAC512(secret);

    public String generateJwtAccessToken(Principal userPrincipal) {
        User user = (User) userPrincipal;


        return JWT.create()
                .withIssuer(GET_ARRAYS_LLC) //firma lub autor
                .withAudience(GET_ARRAYS_ADMINISTRATION) //dla kogo?
                .withIssuedAt(new Date()) //kiedy
                .withSubject(userPrincipal.getName()) //musi być unique - username
//                .withArrayClaim(AUTHORITIES, claims) //uprawnienia
                .withClaim(AUTHORITIES, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//do kiedy
                .sign(Algorithm.HMAC512(secret));
    }

    public String generateJwtRefreshToken(Principal userPrincipal) {
        User user = (User) userPrincipal;

        return JWT.create()
                .withIssuer(GET_ARRAYS_LLC) //firma lub autor
                .withAudience(GET_ARRAYS_ADMINISTRATION) //dla kogo?
                .withIssuedAt(new Date()) //kiedy
                .withSubject(userPrincipal.getName()) //musi być unique
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESH))//do kiedy
                .sign(Algorithm.HMAC512(secret));
    }

    public UsernamePasswordAuthenticationToken verifyJwtToken(String token) {
        int i=0;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secret)).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim(AUTHORITIES).asArray(String.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));

        });

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        return authenticationToken;
    }

}

package net.focik.userservice.domain.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import net.focik.userservice.domain.SecureUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static net.focik.userservice.domain.security.constant.SecurityConstant.*;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtAccessToken(SecureUser secureUser) {
        String[] claims = getClaimsFromUser(secureUser);

        return JWT.create()
                .withIssuer(GET_ARRAYS_LLC) //firma lub autor lub nazwa app
                .withAudience(GET_ARRAYS_ADMINISTRATION) //dla kogo?
                .withIssuedAt(new Date()) //kiedy
                .withSubject(secureUser.getUsername()) //musi być unique - username
//                .withArrayClaim(AUTHORITIES, claims) //uprawnienia
                .withClaim(AUTHORITIES, secureUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//do kiedy
                .sign(Algorithm.HMAC512(secret));
    }

    private String[] getClaimsFromUser(SecureUser user) {
        List<String> authorities = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }

        return authorities.toArray(new String[0]);
    }

    public List<GrantedAuthority> getAuthorities(String token) {

        String[] claims = getClaimsFromToken(token);

        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(username, null, authorities);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;
    }

    public boolean isTokenValid(String username, String token) {
        JWTVerifier verifier = getJwtVerifier();

        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }


    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }


    private JWTVerifier getJwtVerifier() {
        JWTVerifier verifier;
        try {
            verifier = JWT.require(Algorithm.HMAC512(secret))
                    .withIssuer(GET_ARRAYS_LLC).build();


        } catch (JWTVerificationException ex) {
            //normalny ex log jako error
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }

        return verifier;
    }

}

package net.focik.userservice.domain.security.constant;

public class SecurityConstant {

    public static final long EXPIRATION_TIME =54_000_000; // 15 godz
//    public static final long EXPIRATION_TIME = 900_000; // 15 min
    public static final long EXPIRATION_TIME_REFRESH = 432_000_000; // 5 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified"; //
    public static final String GET_ARRAYS_LLC = "Progas";
    public static final String GET_ARRAYS_ADMINISTRATION = "User Management Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/api/auth/login"};//, "/api/user/register"}; //można potem dodać
//    public static final String[] PUBLIC_URLS = {"**"}; //można potem dodać

}

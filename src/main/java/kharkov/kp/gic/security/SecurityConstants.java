package kharkov.kp.gic.security;

public class SecurityConstants {
	public static final String SECRET = "WhoAreYouToFuckingLectureMe";
    public static final long EXPIRATION_TIME = 3 * (24*60*60*10000L); // 3 дней в миллисекундах
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/personnel/api/v1/sign-up";
}

// https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
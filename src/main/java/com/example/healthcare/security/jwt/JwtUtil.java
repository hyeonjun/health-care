package com.example.healthcare.security.jwt;

import com.example.healthcare.account.domain.code.AuthorityType;
import com.example.healthcare.account.repository.UserRepository;
import com.example.healthcare.redis.RedisUtil;
import com.example.healthcare.security.user.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtil {
    // Header KEY 값

    public static final String TOKEN = "TOKEN";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    private final RedisTemplate redisTemplate;

    // accessToken 만료시간
    private final long ACCESS_TOKEN_TIME = 1L;//7 * 24 * 60 * 60 * 1000L; // 7일

    // refreshToken 만료시간
    private final long REFRESH_TOKEN_TIME = 30 * 24 * 60 * 60 * 1000L; // 30일

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private SecretKey key;

    private final RedisUtil redisUtil;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;


    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

        @PostConstruct
        public void init() {
            byte[] bytes = Base64.getDecoder().decode(secretKey);
            key = Keys.hmacShaKeyFor(bytes);
        }

    public String createAccessToken(String email, AuthorityType authorityType) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(email) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, authorityType)
                        .expiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                        .issuedAt(date) // 발급일
                        .signWith(key) // 암호화 알고리즘
                        .compact();
    }

    public String createRefreshToken(String email) {
        Date now = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(email) // 사용자 식별자값(ID)
                        .expiration(new Date(now.getTime() + REFRESH_TOKEN_TIME)) // 만료 시간
                        .issuedAt(now) // 발급일
                        .signWith(key) // 암호화 알고리즘
                        .compact();
    }

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        // Cookie Value 에는 공백이 불가능해서 encoding 진행
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        Cookie cookie = new Cookie(TOKEN, token); // Name-Value
        cookie.setMaxAge(60 * 60); // 60초 60분 1시간
        cookie.setPath("/");
        // ResponseHeader에 token 추가
        res.addHeader(TOKEN, token);
        // Response 객체에 Cookie 추가
        res.addCookie(cookie);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            if (redisUtil.hasKeyBlackList(token)) {
                throw new RuntimeException("logout된 아이디입니다.");
            }
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new JwtException("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new JwtException("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new JwtException("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new JwtException("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
    }


    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    // JWT 가져오기
    public String getTokenFromRequest(HttpServletRequest req) {
            // 쿠키에서 가져오기
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(TOKEN)) {
//                        System.out.println(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8));
//                        // Encode 되어 넘어간 Value(공백 인코딩) 다시 Decode
//                        return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
//                }
//            }
//        }
        // 헤더에서 가져오기
        return Optional.ofNullable(req.getHeader(TOKEN))
                .map(header -> URLDecoder.decode(header, StandardCharsets.UTF_8))
                .orElseThrow(() -> new NullPointerException("토큰이 존재하지 않습니다. 로그인 해주세요."));
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("토큰이 존재하지 않습니다. 로그인 해주세요.");
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORIZATION_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORIZATION_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public long getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload().getExpiration();
        // 현재 시간
        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}

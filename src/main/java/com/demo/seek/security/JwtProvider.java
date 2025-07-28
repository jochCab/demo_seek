package com.demo.seek.security;

import com.demo.seek.dto.JwtDto;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class.getSimpleName());

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    // Clave HMAC generada desde el secret
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
public String generateToken(Authentication authentication) {
    UsuarioLogueado usuarioPrincipal = (UsuarioLogueado) authentication.getPrincipal();
    
    // Manejo de authorities null
    List<String> permisos = Optional.ofNullable(usuarioPrincipal.getAuthorities())
        .orElseGet(Collections::emptyList)
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return Jwts.builder()
            .setSubject(usuarioPrincipal.getUsername())
            .claim("email", usuarioPrincipal.getEmail())
            .claim("permisos", permisos)
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + expiration * 180))
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
}
    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email",  String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Error validando token: {}", e.getMessage());
            return false;
        }
    }

    public String refreshToken(JwtDto jwtDto) throws ParseException {
        JWT jwt = JWTParser.parse(jwtDto.getToken());
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        String nombreUsuario = claims.getSubject();
        List<String> roles = (List<String>) claims.getClaim("roles");

        return Jwts.builder()
                .setSubject(nombreUsuario)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
}
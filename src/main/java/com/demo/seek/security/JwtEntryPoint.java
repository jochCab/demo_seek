package com.demo.seek.security;

import com.demo.seek.util.Constantes;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class.getSimpleName());



    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse res, AuthenticationException authException) throws IOException{
        logger.error(Constantes.NO_AUTORIZADO);
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }
        logger.error("IP: " + ipAddress);
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constantes.NO_AUTORIZADO);
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");
    }
}
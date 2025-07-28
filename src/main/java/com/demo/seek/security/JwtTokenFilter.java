package com.demo.seek.security;

import com.demo.seek.security.service.UserDetailsServiceImpl;
import jakarta.servlet.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class.getSimpleName());

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    private String getToken(jakarta.servlet.http.HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }


    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        try {
            // Obtener el path de la solicitud
            String path = request.getRequestURI();

            // Excluir las rutas que no necesitan autenticación
            if (path.startsWith("/auth/restaurar") || path.startsWith("/auth")) {
                logger.info("Ruta sin autenticación: " + path);
                filterChain.doFilter(request, response);
                return; // No seguir con la validación del token para estas rutas
            }

            // Obtener el token del request
            String token = getToken(request);
            if (token != null && jwtProvider.validateToken(token)) {
                logger.info("TOKEN NO ES NULL");
                logger.info("TOKEN: " + token);
                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                logger.info("Nombre Usuario: " + nombreUsuario);
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

                // Autenticar al usuario
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            logger.error("Error en el método doFilter: " + e.getMessage());
        }
        // Continuar con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }

}


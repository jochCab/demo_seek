package com.demo.seek.controller;

import com.demo.seek.dto.JwtDto;
import com.demo.seek.dto.LoginUsuario;
import com.demo.seek.dto.NuevoUsuarioDto;
import com.demo.seek.dto.ResultadoDto;
import com.demo.seek.entity.Usuario;
import com.demo.seek.security.JwtProvider;

import com.demo.seek.service.UsuarioService;
import com.demo.seek.util.Constantes;
import com.demo.seek.util.ExceptionDemoSeek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController extends BaseController {

    private final static Logger logger = LoggerFactory
            .getLogger(Constantes.NOMBRE_APP + Constantes.DIVISOR_GUION_MEDIO + AuthController.class.getSimpleName());

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;

    @Autowired
    @Lazy
    UsuarioService usuarioService;



    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResultadoDto<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario) {

        ResultadoDto resultadoDto = new ResultadoDto();

        try {
            logger.info("Iniciando... ");
            // if (bindingResult.hasErrors()) throw new ExceptionDemoSeek("Campos mal
            // formados", null);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            JwtDto jwtDto = new JwtDto(jwt);
            resultadoDto.setData(jwtDto);
            resultadoDto.setError(false);
            resultadoDto.setMensaje("Bienvenido...");
            logger.info("Inicio sesión " + loginUsuario.getEmail());
        } catch (BadCredentialsException e) {
            resultadoDto.setError(true);
            resultadoDto.setCodigo(-2);
            resultadoDto.setMensaje("Verifique sus credenciales");
            logger.error(resultadoDto.getMensaje(), e);
        } catch (Exception e) {
            resultadoDto.setError(true);
            resultadoDto.setCodigo(-1);
            resultadoDto.setMensaje("ERROR al iniciar sesión.");
            logger.error(resultadoDto.getMensaje(), e);
        }

        return resultadoDto;
    }

    @PostMapping("/nuevo")
    public ResultadoDto nuevo(@Valid @RequestBody NuevoUsuarioDto nuevoUsuario, BindingResult bindingResult) {
        ResultadoDto resultadoDto = new ResultadoDto();
        try {
            if (bindingResult.hasErrors())
                throw new ExceptionDemoSeek("Campos mal puestos o email inválido", null);

            // Validación adicional del formato de fecha
            if (!isValidTimestampFormat(nuevoUsuario.getFechaNacimiento())) {
                throw new ExceptionDemoSeek("Formato de fecha inválido. Use yyyy-MM-dd HH:mm:ss", null);
            }

            if (usuarioService.existsByEmail(nuevoUsuario.getEmail()))
                throw new ExceptionDemoSeek("El Email ya existe", null);

            // Conversión segura de la fecha
            LocalDateTime fechaNacimiento = parseFechaNacimiento(nuevoUsuario.getFechaNacimiento());

            Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(),
                    Timestamp.valueOf(fechaNacimiento), true, nuevoUsuario.getDocumento(), nuevoUsuario.getEmail(),
                    passwordEncoder.encode(nuevoUsuario.getPassword()));
    

            usuarioService.save(usuario);

            resultadoDto.setError(false);
            resultadoDto.setMensaje("El usuario fue registrado");
            resultadoDto.setCodigo(0);
        } catch (ExceptionDemoSeek e) {
            resultadoDto.setError(true);
            resultadoDto.setMensaje(e.getMessage());
            resultadoDto.setCodigo(-1);
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            resultadoDto.setError(true);
            resultadoDto.setCodigo(-1);
            resultadoDto.setMensaje("ERROR al guardar los datos.");
            logger.error(resultadoDto.getMensaje(), e);
        }
        return resultadoDto;
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDto);
        JwtDto jwt = new JwtDto(token);
        return new ResponseEntity(jwt, HttpStatus.OK);
    }




    // Método auxiliar para validar el formato
    private boolean isValidTimestampFormat(String fecha) {
        try {
            LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return true;
        } catch (DateTimeParseException e) {
            try {
                LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                return true;
            } catch (DateTimeParseException ex) {
                return false;
            }
        }
    }

    // Método para convertir de String a LocalDateTime
    private LocalDateTime parseFechaNacimiento(String fechaStr) {
        try {
            return LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (DateTimeParseException e) {
            return LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        }

    }
}
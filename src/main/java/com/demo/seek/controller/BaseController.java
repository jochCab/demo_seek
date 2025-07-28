package com.demo.seek.controller;

import com.demo.seek.dto.ResultadoDto;
import com.demo.seek.util.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {

    private final static Logger logger = LoggerFactory.getLogger(Constantes.NOMBRE_APP+Constantes.DIVISOR_GUION_MEDIO+ BaseController.class.getSimpleName());

    public void logIp (HttpServletRequest req, String correo){
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }
        logger.info("IP: " + ipAddress + ",CORREO:"+correo);

    }

    public ResultadoDto manejarError(ResultadoDto resultadoDto, String mensaje, Exception e) {
        resultadoDto.setError(true);
        resultadoDto.setCodigo(-1);
        resultadoDto.setMensaje(mensaje);
        logger.error(mensaje, e);
        return resultadoDto;
    }
}

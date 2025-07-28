package com.demo.seek.controller;

import java.util.Optional;

import com.demo.seek.dto.ResultadoDto;
import com.demo.seek.entity.Cliente;
import com.demo.seek.service.ClienteService;
import com.demo.seek.util.Constantes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes")
@RestController
@RequestMapping("/clientes")
@CrossOrigin
public class ClienteController extends BaseController  {

    private final static Logger logger = LoggerFactory.getLogger(Constantes.NOMBRE_APP + Constantes.DIVISOR_GUION_MEDIO + ClienteController.class.getSimpleName());

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista de todos los clientes registrados")
    @GetMapping("/listar")
    public ResultadoDto<Page<Cliente>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {  // Agregamos el parámetro de búsqueda
        
        ResultadoDto<Page<Cliente>> resultadoDto= new ResultadoDto<Page<Cliente>>();
        try {
            Pageable pageable = PageRequest.of(page, size);
            
            Page<Cliente> clientes;
            if (search != null && !search.isEmpty()) {
                clientes = clienteService.findBySearch(search, pageable); 
            } else {
                clientes = clienteService.findAll(pageable); // Si no hay búsqueda, obtenemos todos los clientes
            }
            
            resultadoDto.setData(clientes);
            resultadoDto.setError(false);
            resultadoDto.setMensaje("Lista de clientes");
            resultadoDto.setCodigo(0);
        } catch (Exception e) {
            resultadoDto = manejarError(resultadoDto, "ERROR al obtener clientes", e);
        }
        return resultadoDto;
    }

    @Operation(summary = "Guardar cliente", description = "Registra los datos de un nuevo cliente")
    @PostMapping("/guardar")
    public ResultadoDto<Cliente> guardarCliente(@RequestBody Cliente cliente) {
        ResultadoDto<Cliente> resultadoDto = new ResultadoDto<Cliente>();
        try {
            Cliente clienteGuardado = clienteService.save(cliente);
            resultadoDto.setData(clienteGuardado);
            resultadoDto.setMensaje("Cliente guardado exitosamente");
            resultadoDto.setError(false);
            resultadoDto.setCodigo(0);
        } catch (Exception e) {
            resultadoDto = manejarError(resultadoDto, "ERROR al guardar cliente", e);
        }
        return resultadoDto;
    }


    @Operation(summary = "Obtener Promedio de edad", description = "Obtiene el promedio de edad de los clientes registrados")
    @GetMapping("/obtenerPromedioEdad")
    public ResultadoDto<Double> obtenerPromedioClientes() {
        ResultadoDto<Double> resultadoDto = new ResultadoDto<Double>();
        try {
            Double promedioClientes = clienteService.obtenerPromedioEdad();
            resultadoDto.setData(promedioClientes);
            resultadoDto.setMensaje("Se obtuvo correctamente el promedio de clientes");
            resultadoDto.setError(false);
            resultadoDto.setCodigo(0);
        } catch (Exception e) {
            resultadoDto = manejarError(resultadoDto, "ERROR al obtener el promedio de clientes", e);
        }
        return resultadoDto;
    }


    @Operation(summary = "Obtener desviacion de edad", description = "Obtiene la desviacion de los clientes registrados")
    @GetMapping("/obtenerDesviacionEdad")
    public ResultadoDto<Double> obtenerVariancionDtoEdad() {
        ResultadoDto<Double> resultadoDto = new ResultadoDto<Double>();
        try {
            Double desviacionClientes = clienteService.obtenerDesviacionEstandarEdad();
            resultadoDto.setData(desviacionClientes);
            resultadoDto.setMensaje("Se obtuvo correctamente los datos desviacion de edad de los clientes");
            resultadoDto.setError(false);
            resultadoDto.setCodigo(0);
        } catch (Exception e) {
            resultadoDto = manejarError(resultadoDto, "ERROR al obtener la desviacion de edad", e);
        }
        return resultadoDto;
    }



}
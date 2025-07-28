package com.demo.seek.service;

import com.demo.seek.entity.Cliente;
import com.demo.seek.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;



    public Page<Cliente> findById(Long id, Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.findById(id, pageable);
        return clientes;
    }

    public Cliente findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        return cliente;
    }

    public Boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);

    }

    public Cliente save(Cliente cliente){
        return clienteRepository.save(cliente);
    }
    public Page<Cliente> findAll(Pageable pageable){
        return clienteRepository.findAll(pageable);
    }

    public Page<Cliente> findBySearch(String search, Pageable pageable) {
        return clienteRepository.findBySearch(search, pageable);
    }

    public Double obtenerDesviacionEstandarEdad(){
        return clienteRepository.obtenerDesviacionEstandarEdad();
    }

    public Double obtenerPromedioEdad() {
        return clienteRepository.obtenerPromedioEdad();
    }   

}

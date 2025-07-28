package com.demo.seek.repository;



import com.demo.seek.entity.Cliente;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c WHERE c.id = :id ORDER BY c.id ASC")
    Page<Cliente> findById(@Param("id") Long id, Pageable pageable);

    @Query("SELECT c FROM Cliente c WHERE c.email = :email ORDER BY c.id ASC")
    Cliente findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    public Page<Cliente> findAll(Pageable pageable);


    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %:search% OR c.apellido LIKE %:search% OR c.documento LIKE %:search% ORDER BY c.id DESC")
    Page<Cliente> findBySearch(@Param("search") String search, Pageable pageable);


    @Query("SELECT AVG(YEAR(CURRENT_DATE) - YEAR(c.fechaNacimiento)) FROM Cliente c")
    Double obtenerPromedioEdad();

    @Query(value = "SELECT STDDEV_POP(TIMESTAMPDIFF(YEAR, c.fechaNacimiento, CURDATE())) FROM Cliente c")
    Double obtenerDesviacionEstandarEdad();

    
}

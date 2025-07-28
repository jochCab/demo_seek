package com.demo.seek.repository;

import com.demo.seek.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.id = :id ORDER BY u.id ASC")
    Page<Usuario> findById(@Param("id") Long id, Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email ORDER BY u.id ASC")
    Usuario findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    public Page<Usuario> findAll(Pageable pageable);


}